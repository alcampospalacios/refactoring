
# Refactorización DNI - Patrones y Principios SOLID

## 📋 Descripción
Refactorización del ejercicio de dni aplicando patrones de diseño y principios SOLID.

---

## 🎨 Patrones de Diseño Aplicados

### 1. **Factory Method**
**Ubicación:** `DocumentoFactory.crearDocumento()`

**Propósito:** Delegar la creación de objetos a subclases específicas según el tipo de documento.

```java
Documento dni = DocumentoFactory.crearDocumento(TIPODNI.DNI, "12345678Z", fecha);
```

### 2. **Strategy** (variante)
**Ubicación:** Cada subclase (`DocumentoDNI`, `DocumentoCIF`, `DocumentoNIE`)

**Propósito:** Encapsula diferentes algoritmos de validación en clases separadas.

**Beneficios:**
- Algoritmos intercambiables en tiempo de ejecución
- Elimina condicionales complejos (switch/if-else)
- Facilita testing individual de cada estrategia

---

## 🏗️ Principios SOLID Aplicados

### **S - Single Responsibility Principle (SRP)**
**Aplicación:** Cada clase tiene una única responsabilidad

| Clase | Responsabilidad |
|-------|----------------|
| `DocumentoDNI` | Validar DNI |
| `DocumentoCIF` | Validar CIF |
| `DocumentoNIE` | Validar NIE |
| `DocumentoFactory` | Crear instancias |

---

### **O - Open/Closed Principle (OCP)**
**Aplicación:** Sistema abierto a extensión, cerrado a modificación

**Ejemplo:** Añadir validación de Pasaporte

---

### **L - Liskov Substitution Principle (LSP)**
**Aplicación:** Cualquier `Documento` puede sustituirse por sus subclases

```java
Documento doc = new DocumentoDNI(...);  // Puede ser cualquier subclase
boolean valido = doc.esvalido();         // Funciona correctamente
```

**Beneficios:**
- Polimorfismo correcto
- Comportamiento predecible
- Interfaces consistentes

---

### **I - Interface Segregation Principle (ISP)**
**Aplicación:** Interfaz mínima y específica

La clase abstracta `Documento` solo expone:
- `esValido()`: método esencial
- Atributos necesarios: `numero`, `fechaValidez`

**Sin métodos innecesarios** que fuercen implementaciones vacías.

---


## 🔄 Principio DRY (Don't Repeat Yourself)

### **Aplicación en Main.java**

**Antes (Código repetido):**
```java
DNI dniCorrecto = new DNI(TIPODNI.DNI, "11111111H", null);
Boolean esValido = (dniCorrecto.validarDNI() == 1);
System.out.println("DNI " + dniCorrecto.numDNI + " es: " + esValido);
// ... repetido 6 veces
```

**Después (DRY):**
```java
List<CasoPrueba> documentos = Arrays.asList(
    new CasoPrueba(TIPODNI.DNI, "11111111H", "DNI correcto"),
    // ...
);
validarDocumentos(documentos);
```

---