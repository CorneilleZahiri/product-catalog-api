# 📦 API de Gestion de Produits et Catégories

API RESTful développée avec **Spring Boot** pour gérer un catalogue de produits et catégories avec des
fonctionnalités avancées.

---

## 🚀 Technologies

- **Java 21** | **Spring Boot 3.5.7** | **MySQL**
- **Spring Data JPA** | **Flyway** | **MapStruct**
- **Bean Validation** | **Swagger/OpenAPI**

---

## 📋 Fonctionnalités

✅ CRUD complet sur produits et catégories  
✅ Pagination et tri des résultats  
✅ Validation des données avec `@Valid`  
✅ Mapping automatique DTOs via MapStruct  
✅ Migrations de base de données avec Flyway  
✅ Optimisation N+1 avec `@EntityGraph`  
✅ Gestion centralisée des erreurs  
✅ Documentation interactive Swagger

---

## 🗄️ Modèle de Données

```Diagramme
    CATEGORIES ||--o{ PRODUCT : contient
    CATEGORIES {
        BIGINT id
        VARCHAR name
        TEXT description
    }
    PRODUCTS {
        BIGINT id
        VARCHAR name
        DECIMAL price
        INT quantity
        BIGINT categories_id
    }
```

---

## ⚙️ Installation

```bash
# Cloner le projet
git clone https://github.com/votre-username/catalog-api.git
```

**API disponible sur** : `http://localhost:8080/api/product`  
**Documentation Swagger** : `http://localhost:8080/api/product/swagger-ui.html`

---

## 🔌 Endpoints de l'API

### 📂 Catégories

| Méthode  | Endpoint               | Description                  |
|----------|------------------------|------------------------------|
| `POST`   | `/api/categories`      | Créer une catégorie          |
| `GET`    | `/api/categories`      | Lister toutes les catégories |
| `GET`    | `/api/categories/{id}` | Voir une catégorie           |
| `PUT`    | `/api/categories/{id}` | Modifier une catégorie       |
| `DELETE` | `/api/categories/{id}` | Supprimer une catégorie      |

### 📦 Produits

| Méthode  | Endpoint             | Description                                      |
|----------|----------------------|--------------------------------------------------|
| `POST`   | `/api/products`      | Créer un produit                                 |
| `GET`    | `/api/products`      | Lister tous les produits (+ filtre `categoryId`) |
| `GET`    | `/api/products/{id}` | Voir un produit                                  |
| `PUT`    | `/api/products/{id}` | Modifier un produit                              |
| `DELETE` | `/api/products/{id}` | Supprimer un produit                             |

### Exemples de Requêtes

**Créer une catégorie**

```bash
POST /api/categories
Content-Type: application/json

{
  "name": "Électronique"
}
```

**Créer un produit**

```bash
POST /api/products
Content-Type: application/json

{
  "name": "Laptop Dell XPS",
  "price": 1299.99,
  "quantity": 15,
  "categoryId": 1
}
```

**Filtrer les produits par catégorie**

```bash
GET /api/products?categoryId=1
```

**Pagination et tri**

```bash
GET /api/products?page=0&size=10&sort=price,desc
GET /api/categories?page=0&size=20&sort=name,asc
```

---

## 📁 Structure du Projet

```
src/main/java/com/example/productapi/
├── controller/          # Contrôleurs REST
├── service/             # Logique métier
├── repository/          # Accès aux données
├── entity/              # Entités JPA
├── dto/                 # DTOs (request/response)
├── mapper/              # MapStruct mappers
├── exception/           # Gestion des erreurs
└── config/              # Configuration

src/main/resources/
└── db/migration/        # Scripts Flyway
    ├── V1__create_categories_table.sql
    └── V2__create_products_table.sql
```

---

## ✅ Bonnes Pratiques Implémentées

- **Architecture en couches** (Controller → Service → Repository)
- **DTOs** avec MapStruct pour isolation des entités
- **Validation stricte** avec Bean Validation
- **Gestion d'erreurs** centralisée avec codes HTTP appropriés
- **Optimisation N+1** via `@EntityGraph`
- **Migrations** versionnées avec Flyway
- **Documentation** automatique avec Swagger

---

## 📖 Documentation

Accédez à la documentation interactive Swagger après avoir lancé l'application :

👉 **http://localhost:8080/swagger-ui.html**

---

## 👤 Auteur

**GBEAGA Corneille**  
📧 cgbeaga@gmail.com  
🔗 [GitHub](https://github.com/CorneilleZahiri)
