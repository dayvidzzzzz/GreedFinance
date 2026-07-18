package dysystem.com.greedfinance.infra.mapper;


import dysystem.com.greedfinance.domain.model.User;
import dysystem.com.greedfinance.infra.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoleMapper.class, TenantMapper.class})
public interface UserMapper {
    @Mapping(target = "active", source = "active")
    User toDomain(UserEntity entity);

    @Mapping(target = "active", source = "active")
    @Mapping(target = "tenant", ignore = true)
    UserEntity toEntity(User domain);
}