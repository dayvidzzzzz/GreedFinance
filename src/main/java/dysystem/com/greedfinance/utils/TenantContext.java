package dysystem.com.greedfinance.utils;

public class TenantContext {
    private static final ThreadLocal<String> currentTenantId = new ThreadLocal<>();

    public static void setCurrentTenantId(String tenantId) {
        currentTenantId.set(tenantId);
    }

    public static String getCurrentTenantId() {
        return currentTenantId.get();
    }

    public static void clear() {
        currentTenantId.remove();
    }
}