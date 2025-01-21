package ciudadano.consciente.utility;

import jakarta.persistence.Table;

public class UtilityMetadataClasses {

    // Auxiliar method to retrieve the table name from the @Table Annotation
    public static <T> String getTableName(Class<T> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = entityClass.getAnnotation(Table.class);
            return tableAnnotation.name(); //
        } else {
            return entityClass.getSimpleName();
        }
    }

}
