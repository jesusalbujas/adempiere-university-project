---
name: Nuevo Reporte de iReport
about: Nuevo Formato de Factura, Nota de Entrega, Reporte General.
title: '[Reporte]: '
labels: 'Atributo: Reporte'
assignees: ''
---

## Descripción del Reporte
Por favor utilice éste campo para llenar alguna breve descripción del reporte solicitado

## Formato y Medidas en Centímetros (cm)
Tomando en cuenta el ejemplo a continuación, rellene los siguientes datos:
![reporte-medidas](https://github.com/erpcya/Control-PLANTILLA/assets/2333092/ebbd90b5-c605-4c11-9b87-a999ba14e263)

- Margen Superior (cm): 
- Margen Inferior (cm): 
- Margen Izquierdo (cm): 
- Margen Derecho (cm): 
- Altura del Encabezado (cm):
- Altura del Contenido (cm): 
- Altura del Pie de Página (cm):
- Imagen con las medidas tomadas

## Datos para Verificación
### Datos Funcionales
- Tipo de Documento:
  - [ ] Orden de Venta
  - [ ] Orden de Compra
  - [ ] Factura de Venta
  - [ ] Factura de Compra
  - [ ] Nota de Crédito
  - [ ] Entrega
  - [ ] Orden de Distribución
  - [ ] Movimiento de Inventario
  - [ ] Orden de Producción
  - [ ] Producción
  - [ ] Inventario de Uso Interno
  - [ ] Inventario Físico
  - [ ] Otro: Se especificará en la descripción
- Número de Documento (Obligatorio): 
- ID del Documento (Obligatorio): 
- Fecha (Obligatorio): 
- Socio de Negocio: 
- Almacén:
- Usuario de Prueba: 
- Password de Prueba: 
- Rol de Prueba: 
### Datos Técnicos
- Link del servicio de ADempiere:
- **ADempiere:**
  - Usuario System:
  - Password System:
- **Base de Datos:**
  - Puerto: 
  - Usuario: 
  - Nombre: 
- Campos nuevos agregados para el reporte y tabla en formato (`C_Invoice.ECAX_XXX`)
### Otros Datos
- Fecha tentativa o esperada en producción por el cliente (Importante crear un **Milestone** para esto)
## ¿Qué se espera con la entrega?
- Nombre del Reporte (`.jasper`): 
- Proceso definido en el ambiente de pruebas:
  - Código del Proceso:
  - Nombre del Proceso:
  - ID del Proceso:

## Consideraciones Finales
- Los datos que se especificaron deben estar en la BD de prueba y servicio compartido 
- Fecha tentativa o esperada por el cliente
- Imagen con las medidas debe lo más explícita posible
