package dysystem.com.greedfinance.service.useCase.account;

import dysystem.com.greedfinance.domain.model.Account;
import dysystem.com.greedfinance.domain.model.Tenant;
import dysystem.com.greedfinance.domain.repository.AccountRepository;
import dysystem.com.greedfinance.domain.repository.TenantRepository;
import dysystem.com.greedfinance.domain.repository.UserRepository;
import dysystem.com.greedfinance.dto.request.AccountRequestDTO;
import dysystem.com.greedfinance.dto.response.AccountResponseDTO;
import dysystem.com.greedfinance.handler.exception.BadRequestException;
import dysystem.com.greedfinance.handler.exception.BusinessException;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CreateAccountUseCase {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;

    @Transactional
    public AccountResponseDTO execute(AccountRequestDTO dto) {
        log.info("=== INICIANDO CRIAÇÃO DE CONTA ===");
        log.info("DTO recebido: {}", dto);

        try {
            // 1. VALIDAR DTO
            log.debug("Validando DTO...");
            if (dto == null) {
                log.error("DTO é null");
                throw new BadRequestException("Dados da conta não podem ser nulos");
            }

            // 2. VALIDAR USERS ID
            log.debug("Validando usersId...");
            if (dto.usersId() == null) {
                log.error("usersId é null");
                throw new BadRequestException("Lista de usuários não pode ser nula");
            }

            log.info("Quantidade de usuários: {}", dto.usersId().size());
            log.info("Users IDs: {}", dto.usersId());

            if (dto.usersId().isEmpty()) {
                log.error("usersId está vazio");
                throw new NotFoundException("Holders list cannot be empty");
            }

            // 3. VALIDAR USUÁRIOS
            log.debug("Validando usuários...");
            List<String> usersIds = dto.usersId();
            for (String holderId : usersIds) {
                log.debug("Buscando usuário: {}", holderId);
                if (userRepository.findById(holderId).isEmpty()) {
                    log.error("Usuário não encontrado: {}", holderId);
                    throw new NotFoundException("User not found: " + holderId);
                }
                log.debug("Usuário encontrado: {}", holderId);
            }
            log.info("Todos os usuários validados com sucesso");

            // 4. VALIDAR ACCOUNT NUMBER
            log.debug("Validando accountNumber: {}", dto.accountNumber());
            if (dto.accountNumber() == null || dto.accountNumber().isEmpty()) {
                log.error("accountNumber é nulo ou vazio");
                throw new BadRequestException("Número da conta não pode ser vazio");
            }

            for (int i = 0; i < dto.accountNumber().length(); i++) {
                char c = dto.accountNumber().charAt(i);
                if (!Character.isDigit(c)) {
                    log.error("Caractere inválido no accountNumber: '{}' na posição {}", c, i);
                    throw new BadRequestException("In account number this character is not a digit " + "'" + c + "'");
                }
            }
            log.debug("accountNumber validado: {}", dto.accountNumber());

            // 5. VALIDAR AGENCY NUMBER
            log.debug("Validando agencyNumber: {}", dto.agencyNumber());
            if (dto.agencyNumber() == null || dto.agencyNumber().isEmpty()) {
                log.error("agencyNumber é nulo ou vazio");
                throw new BadRequestException("Número da agência não pode ser vazio");
            }

            for (int i = 0; i < dto.agencyNumber().length(); i++) {
                char c = dto.agencyNumber().charAt(i);
                if (!Character.isDigit(c)) {
                    log.error("Caractere inválido no agencyNumber: '{}' na posição {}", c, i);
                    throw new BadRequestException("In agency number this character is not a digit " + "'" + c + "'");
                }
            }
            log.debug("agencyNumber validado: {}", dto.agencyNumber());

            // 6. VERIFICAR CONTA DUPLICADA
            log.debug("Verificando se accountNumber já existe: {}", dto.accountNumber());
            if (accountRepository.findByAccountNumber(dto.accountNumber()).isPresent()) {
                log.error("AccountNumber já existe: {}", dto.accountNumber());
                throw new BusinessException("There´s already a account with this account number");
            }
            log.debug("AccountNumber disponível");

            // 7. OBTER TENANT ID
            log.info("Obtendo tenantId do SecurityUtils...");
            String tenantId = SecurityUtils.getCurrentTenantId();
            log.info("TenantId obtido: {}", tenantId);

            if (tenantId == null || tenantId.isEmpty()) {
                log.error("TenantId é nulo ou vazio!");
                throw new BusinessException("Tenant ID não encontrado. Verifique a autenticação.");
            }

            // 8. BUSCAR TENANT
            log.debug("Buscando tenant com ID: {}", tenantId);
            Tenant tenant = tenantRepository.findById(tenantId)
                    .orElseThrow(() -> {
                        log.error("Tenant não encontrado: {}", tenantId);
                        return new NotFoundException("Tenant not found: " + tenantId);
                    });
            log.info("Tenant encontrado: {}", tenant.getName());

            // 9. CRIAR CONTA
            log.info("Gerando UUID para nova conta...");
            String accountId = UUID.randomUUID().toString();
            log.info("Account ID gerado: {}", accountId);

            log.debug("Construindo objeto Account...");
            Account.AccountBuilder accountBuilder = Account.builder()
                    .id(accountId)
                    .name(dto.name())
                    .description(dto.description())
                    .balance(dto.balance() != null ? dto.balance() : java.math.BigDecimal.ZERO)
                    .type(dto.type())
                    .accountNumber(dto.accountNumber())
                    .agencyNumber(dto.agencyNumber())
                    .holderIds(usersIds)
                    .tenantId(tenantId);
            // 10. DEFINIR CONTA PADRÃO
            List<String> existingAccounts = tenant.getAccountsId();
            log.info("Quantidade de contas existentes no tenant: {}",
                    existingAccounts != null ? existingAccounts.size() : 0);

            if (existingAccounts == null || existingAccounts.isEmpty()) {
                log.info("Nenhuma conta existente. Definindo como conta padrão.");
                accountBuilder.defaultAccount(true);
            } else {
                log.info("Já existem contas. Não será conta padrão.");
                accountBuilder.defaultAccount(false);
            }

            // 11. SALVAR CONTA
            log.info("Salvando conta no repositório...");
            Account savedAccount = accountRepository.save(accountBuilder.build());
            log.info("Conta salva com sucesso! ID: {}", savedAccount.getId());

            // 12. RETORNAR RESPOSTA
            AccountResponseDTO response = toResponse(savedAccount);
            log.info("Resposta gerada: {}", response);
            log.info("=== CRIAÇÃO DE CONTA CONCLUÍDA COM SUCESSO ===");

            return response;

        } catch (Exception e) {
            log.error("=== ERRO NA CRIAÇÃO DA CONTA ===");
            log.error("Tipo da exceção: {}", e.getClass().getName());
            log.error("Mensagem: {}", e.getMessage());
            log.error("Stack trace:", e);
            throw e;
        }
    }

    private AccountResponseDTO toResponse(Account account) {
        log.debug("Convertendo Account para Response DTO");
        return new AccountResponseDTO(
                account.getId(),
                account.getName(),
                account.getDescription(),
                account.getBalance(),
                account.getType(),
                account.getAgencyNumber(),
                account.isActive(),
                account.isDefaultAccount(),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }
}