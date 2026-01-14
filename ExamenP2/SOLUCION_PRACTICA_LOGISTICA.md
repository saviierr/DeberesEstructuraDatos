# Guía de Solución: Sistema de Gestión de Envíos (Logística)

Esta guía presenta el pseudocódigo para la implementación de los módulos requeridos en el examen. Se enfoca en la construcción de estructuras de datos desde cero ("from scratch").

## 1. Abstracción y Genéricos

### Interfaz Genérica
Define el contrato para cualquier contenedor de datos.

```pseudocode
INTERFACE IContainer<T>
    METHOD add(element: T): VOID
    METHOD remove(element: T): BOOLEAN
    METHOD get(id: STRING): T
END INTERFACE
```

### Clase Paquete
Implementa la lógica base para los diferentes tipos de carga.

```pseudocode
CLASS Package<T>
    PROPERTY id: STRING
    PROPERTY content: T
    PROPERTY type: ENUM (FRAGILE, STANDARD, EXPRESS)
    PROPERTY priority: INTEGER  // Para el ordenamiento luego

    CONSTRUCTOR(id, content, type, priority)
        THIS.id = id
        THIS.content = content
        THIS.type = type
        THIS.priority = priority
    END CONSTRUCTOR

    METHOD toString(): STRING
        RETURN "Package CL: " + id + " [" + type + "]"
    END METHOD
END CLASS
```

## 2. Rutas Flexibles (Lista Doblemente Enlazada)

Se requiere una Lista Doblemente Enlazada (DLL) para permitir navegación bidireccional.

### Nodo de la Lista
```pseudocode
CLASS Node<T>
    PROPERTY data: T
    PROPERTY next: Node<T>
    PROPERTY prev: Node<T>

    CONSTRUCTOR(data)
        THIS.data = data
        THIS.next = NULL
        THIS.prev = NULL
    END CONSTRUCTOR
END CLASS
```

### Gestor de Rutas (RouteManager)
```pseudocode
CLASS RouteManager<T> IMPLEMENTS IContainer<T>
    PROPERTY head: Node<T>
    PROPERTY tail: Node<T>
    PROPERTY current: Node<T>  // Para la navegación

    CONSTRUCTOR()
        head = NULL
        tail = NULL
        current = NULL
    END CONSTRUCTOR

    // Insertar al final O(1)
    METHOD add(element: T)
        newNode = NEW Node(element)
        IF head IS NULL THEN
            head = newNode
            tail = newNode
            current = newNode
        ELSE
            tail.next = newNode
            newNode.prev = tail
            tail = newNode
        END IF
    END METHOD

    // Eliminar envío cancelado O(1) asumiendo referencia directa, 
    // o O(n) si se busca por valor. Aquí mostramos búsqueda por valor para IContainer.
    METHOD remove(element: T): BOOLEAN
        temp = head
        WHILE temp IS NOT NULL DO
            IF temp.data EQUALS element THEN
                // Lógica de desconexión de punteros
                IF temp.prev IS NOT NULL THEN
                    temp.prev.next = temp.next
                ELSE
                    head = temp.next // Era la cabeza
                END IF

                IF temp.next IS NOT NULL THEN
                    temp.next.prev = temp.prev
                ELSE
                    tail = temp.prev // Era la cola
                END IF
                
                RETURN TRUE
            END IF
            temp = temp.next
        END WHILE
        RETURN FALSE
    END METHOD

    // Navegación O(1)
    METHOD moveNext()
        IF current IS NOT NULL AND current.next IS NOT NULL THEN
            current = current.next
            PRINT "Movido a siguiente punto"
        END IF
    END METHOD

    METHOD movePrev()
        IF current IS NOT NULL AND current.prev IS NOT NULL THEN
            current = current.prev
            PRINT "Retorno a punto previo"
        END IF
    END METHOD
END CLASS
```

## 3. Inventario Rápido (Tabla Hash)

Implementación propia de una Tabla Hash para búsquedas O(1). Se usa encadenamiento (listas enlazadas) para colisiones.

```pseudocode
CLASS HashTable<K, V>
    PROPERTY capacity: INTEGER
    PROPERTY buckets: ARRAY OF LinkedList<Entry<K, V>>
    PROPERTY size: INTEGER

    CONSTRUCTOR(initialCapacity)
        capacity = initialCapacity
        buckets = NEW ARRAY[capacity]
        size = 0
    END CONSTRUCTOR

    // Función Hash simple
    METHOD getHash(key: K): INTEGER
        hash = key.hashCode()
        RETURN ABS(hash) MOD capacity
    END METHOD

    METHOD put(key: K, value: V)
        index = getHash(key)
        IF buckets[index] IS NULL THEN
            buckets[index] = NEW LinkedList()
        END IF

        // Verificar si la clave ya existe y actualizar
        FOR EACH entry IN buckets[index] DO
            IF entry.key EQUALS key THEN
                entry.value = value
                RETURN
            END IF
        END FOR

        // Insertar nuevo si no existe
        buckets[index].add(NEW Entry(key, value))
        size = size + 1
    END METHOD

    METHOD get(key: K): V
        index = getHash(key)
        IF buckets[index] IS NOT NULL THEN
            FOR EACH entry IN buckets[index] DO
                IF entry.key EQUALS key THEN
                    RETURN entry.value
                END IF
            END FOR
        END IF
        RETURN NULL
    END METHOD
END CLASS

// Clase auxiliar para guardar clave-valor
CLASS Entry<K, V>
    PROPERTY key: K
    PROPERTY value: V
    CONSTRUCTOR(key, value)...
END CLASS
```

## 4. Optimización Prioritaria (Sorting)

Comparación de algoritmos de ordenamiento.

```pseudocode
CLASS PrioritySorter
    
    // Insertion Sort: O(n^2) peor caso, pero O(n) si está casi ordenado.
    // Bueno para listas pequeñas o cuando los datos van llegando poco a poco.
    METHOD insertionSort(packages: ARRAY OF Package)
        n = packages.LENGTH
        FOR i FROM 1 TO n - 1 DO
            key = packages[i]
            j = i - 1
            WHILE j >= 0 AND packages[j].priority > key.priority DO
                packages[j + 1] = packages[j]
                j = j - 1
            END WHILE
            packages[j + 1] = key
        END FOR
    END METHOD

    // Selection Sort: O(n^2) siempre.
    // Realiza menos intercambios (writes) que otros algoritmos O(n^2).
    METHOD selectionSort(packages: ARRAY OF Package)
        n = packages.LENGTH
        FOR i FROM 0 TO n - 1 DO
            minIndex = i
            FOR j FROM i + 1 TO n - 1 DO
                IF packages[j].priority < packages[minIndex].priority THEN
                    minIndex = j
                END IF
            END FOR
            
            // Swap
            temp = packages[minIndex]
            packages[minIndex] = packages[i]
            packages[i] = temp
        END FOR
    END METHOD
END CLASS
```

## 5. Análisis de Complejidad (Big O)

Justificación teórica de las estructuras elegidas.

### RouteManager (Doubly Linked List)
*   **Insertar (Add)**: **O(1)**. Al mantener un puntero `tail`, agregar al final es inmediato, no hay que recorrer la lista.
*   **Eliminar (Remove)**: **O(1)** si tenemos la referencia directa al nodo (solo reajustamos punteros prev/next). **O(n)** si debemos buscar el nodo por valor.
*   **Navegar (Next/Prev)**: **O(1)**. Solo movemos el puntero `current` un paso.

### InventoryMap (Hash Table)
*   **Insertar (Put)**: **O(1)** promedio. Si hay muchas colisiones, puede degradarse a O(n), pero con una buena función hash es constante.
*   **Buscar (Get)**: **O(1)** promedio. Acceso directo mediante el índice calculado por el hash.

### PrioritySorter
*   **Insertion Sort**: **O(n^2)**. Elegido porque es eficiente para listas pequeñas o "casi ordenadas", un escenario común en logística donde los envíos suelen llegar con cierto orden temporal.
*   **Selection Sort**: **O(n^2)**. Elegido para comparación. Aunque tiene la misma complejidad temporal, minimiza el número de escrituras en memoria (swaps), lo cual puede ser útil si escribir en memoria es costoso (ej. sistemas embebidos antiguos), aunque generalmente Insertion Sort es preferible en software moderno.
