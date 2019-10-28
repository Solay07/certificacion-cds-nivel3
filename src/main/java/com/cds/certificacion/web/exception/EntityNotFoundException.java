package com.cds.certificacion.web.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

public class EntityNotFoundException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Constructor EntityNotFoundException
   * 
   * @param clazz
   * @param searchParamsMap
   */
  public EntityNotFoundException(Class<?> clazz, Object... searchParamsMap) {
    super(EntityNotFoundException.generateMessage(clazz.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
  }

  /**
   * Generate message for not found resource
   * 
   * @param entity - the name of entity that throw exception
   * @param searchParams
   * @return String  - the generated message
   */
  private static String generateMessage(String entity, Map<String, String> searchParams) {
    return StringUtils.capitalize(entity) + " was not found for parameters " + searchParams;
  }
  
  /**
   * Map the _params to show in generated message
   * 
   * @param keyType - the type of key map returned
   * @param valueType - the type of value map returned
   * @param entries - the _params to map
   * @return Map<K, V> - the _params mapped
   */
  private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, Object... entries) {
    
    if (entries.length % 2 == 1) throw new IllegalArgumentException("Invalid entries");
    
    return IntStream.range(0, entries.length / 2).map(i -> i * 2)
            .collect(HashMap::new,
                    (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
                    Map::putAll);
  }

}
