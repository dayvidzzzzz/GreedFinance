package dysystem.com.greedfinance.infra.entity;

import dysystem.com.greedfinance.enums.ContributionType;
import dysystem.com.greedfinance.enums.SavingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "savings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "tenantId", type = String.class))
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
public class SavingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    private BigDecimal currentAmount;
    private BigDecimal targetAmount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "target_date")
    private LocalDateTime targetDate;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "concluded_at")
    private LocalDateTime concludedAt;

    @Column(name = "saving_status")
    @Enumerated(EnumType.STRING)
    private SavingStatus status;

    @Column(name = "contribution_type")
    @Enumerated(EnumType.STRING)
    private ContributionType contributionType;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private TenantEntity tenant;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    //esse relacionamento esta assim, pois é unidirecional, so mostrando os ‘ids’ das transações
    @ElementCollection
    @CollectionTable(name = "saving_transaction_ids",
            joinColumns = @JoinColumn(name = "saving_id"))
    @Column(name = "transaction_id")
    private List<Long> transactionIds = new ArrayList<>();

    @Column(name = "allow_early_withdrawal")
    private boolean allowEarlyWithdrawal;

    @PrePersist
    protected void onCreation(){
        if (this.createdAt == null)
            this.createdAt = LocalDateTime.now();
        if (this.targetDate == null)
            this.targetDate = LocalDateTime.now();
        if (this.updatedAt == null)
            this.updatedAt = LocalDateTime.now();
        if (this.currentAmount == null)
            this.currentAmount = BigDecimal.ONE;
        if (this.status == null)
            this.status = SavingStatus.ACTIVE;
        if (this.contributionType == null)
            this.contributionType = ContributionType.RECURRENT;

        this.allowEarlyWithdrawal = true;
    }

}