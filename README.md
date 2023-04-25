# spring-backend-apirest
# API REST CRUD de clientes con Java y Spring Boot

Este proyecto consiste en el desarrollo de una API REST de un CRUD de clientes, utilizando el framework Spring Boot y una arquitectura en capas. La API permite crear, leer, actualizar y eliminar clientes, y está diseñada siguiendo los principios de REST.
## Consideraciones
La capa de acceso a datos está implementada utilizando Spring Data y una base de datos MySQL. Además, se ha incorporado el patrón DTO para separar la capa de presentación de la capa de acceso a datos, y se ha utilizado Lombok para generar automáticamente getters, setters y constructores.

También se ha incluido la validación de datos utilizando Java Bean Validator, lo que garantiza que los datos enviados a la API cumplen con las restricciones definidas en las entidades.

## Features

Por último, se ha habilitado la configuración CORS para permitir el acceso a la API desde [ANGULAR](https://github.com/Rpeche-pk/angular-clientes-app.git).

Este proyecto puede ser utilizado como base para el desarrollo de otras APIs REST utilizando Java y Spring Boot, y se espera que sea de utilidad para aquellos que buscan aprender sobre el desarrollo de APIs REST con Spring Boot.

## API Reference

### **Get all customer**

```http
  GET /api/clientes
```
| Response | 200 OK |      |
| :-------- | :------- | :-------------------------------- |

### **Get customer by id**

```http
  GET /api/clientes/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Required**. Id of customer to fetch |

| Response | 200 OK |   404 Not Found                  |
| :-------- | :------- | :-------------------------------- |

### **Create customer**

```http
  http://localhost:8080/api/clientes
```

| Response | 201 Created |   400 Bad Request                   |
| :-------- | :------- | :-------------------------------- |


### **Update customer**
```http
  PUT /api/clientes/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Required**. Id of customer to update |

| Response | 200 OK |   404 Not Found                  |
| :-------- | :------- | :-------------------------------- |

### **Delete customer**
```http
  DELETE /api/clientes/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Required**. Id of customer to delete |

| Response |  |   204 No Content                 |
| :-------- | :------- | :-------------------------------- |


## ANGULAR BACKEND
[Proyecto ANGULAR](https://github.com/Rpeche-pk/angular-clientes-app.git)
