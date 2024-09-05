package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.example.entidades.*;
import lombok.*;
import javax.persistence.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Crear el EntityManagerFactory usando la unidad de persistencia definida en persistence.xml
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        // Crear un EntityManager para realizar operaciones de persistencia
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("Aplicación en marcha");

        try {
            // Inicia una transacción para operaciones de persistencia
            entityManager.getTransaction().begin();

            // Creación de instancias de entidades
            Domicilio domicilio1 = Domicilio.builder().nombre_calle("Negra Arroyo Lane").numero(308).build();
            Domicilio domicilio2 = Domicilio.builder().nombre_calle("Calle de Los Pollos").numero(129).build();

            Cliente cliente1 = Cliente.builder().nombre("Walter").apellido("White").dni(12345678).domicilio(domicilio1).build();
            Cliente cliente2 = Cliente.builder().nombre("Jesse").apellido("Pinkman").dni(87654321).domicilio(domicilio2).build();

            Categoria categoria1 = Categoria.builder().denominacion("Química").build();
            Categoria categoria2 = Categoria.builder().denominacion("Limpieza").build();

            Articulo articulo1 = Articulo.builder().cantidad(100).denominacion("Ácido Fluorhídrico").precio(10000).categorias(List.of(categoria1)).build();
            Articulo articulo2 = Articulo.builder().cantidad(50).denominacion("Mezcla Especial").precio(20000).categorias(List.of(categoria1)).build();
            Articulo articulo3 = Articulo.builder().cantidad(200).denominacion("Detergente Azul").precio(3000).categorias(List.of(categoria2)).build();
            Articulo articulo4 = Articulo.builder().cantidad(75).denominacion("Jabón de Manos").precio(500).categorias(List.of(categoria2)).build();

            DetalleFactura detalleFactura1 = DetalleFactura.builder().cantidad(10).subtotal(100000).build();
            DetalleFactura detalleFactura2 = DetalleFactura.builder().cantidad(5).subtotal(100000).build();
            DetalleFactura detalleFactura3 = DetalleFactura.builder().cantidad(2).subtotal(6000).build();
            DetalleFactura detalleFactura4 = DetalleFactura.builder().cantidad(4).subtotal(2000).build();

            Factura factura1 = Factura.builder().fecha("15/01/2023").numero(3456).precio(200000).cliente(cliente1).detalles(List.of(detalleFactura1, detalleFactura2)).build();
            Factura factura2 = Factura.builder().fecha("20/02/2024").numero(7890).precio(8000).cliente(cliente2).detalles(List.of(detalleFactura3, detalleFactura4)).build();

            // Establecimiento de relaciones entre entidades
            detalleFactura1.setFactura(factura1);
            detalleFactura2.setFactura(factura1);
            detalleFactura3.setFactura(factura2);
            detalleFactura4.setFactura(factura2);

            categoria1.setArticulos(List.of(articulo1, articulo2));
            categoria2.setArticulos(List.of(articulo3, articulo4));

            // Persistencia de las entidades en la base de datos
            entityManager.persist(cliente1);
            entityManager.persist(cliente2);
            entityManager.persist(articulo1);
            entityManager.persist(articulo2);
            entityManager.persist(articulo3);
            entityManager.persist(articulo4);
            entityManager.persist(detalleFactura1);
            entityManager.persist(detalleFactura2);
            entityManager.persist(detalleFactura3);
            entityManager.persist(detalleFactura4);
            entityManager.persist(factura1);
            entityManager.persist(factura2);
            entityManager.persist(categoria1);
            entityManager.persist(categoria2);
            entityManager.persist(domicilio1);
            entityManager.persist(domicilio2);

            // Asegura que todas las operaciones de persistencia se realicen
            entityManager.flush();
            entityManager.getTransaction().commit();

            // Consultar y mostrar la entidad persistida
            Cliente retrievedCliente1 = entityManager.find(Cliente.class, cliente1.getId());
            System.out.println("Cliente 1: Nombre: " + retrievedCliente1.getNombre() + ", Apellido: " + retrievedCliente1.getApellido() + ", DNI: " + retrievedCliente1.getDni() + ", Domicilio: " + retrievedCliente1.getDomicilio().getNombre_calle() + " " + retrievedCliente1.getDomicilio().getNumero());

            Cliente retrievedCliente2 = entityManager.find(Cliente.class, cliente2.getId());
            System.out.println("Cliente 2: Nombre: " + retrievedCliente2.getNombre() + ", Apellido: " + retrievedCliente2.getApellido() + ", DNI: " + retrievedCliente2.getDni() + ", Domicilio: " + retrievedCliente2.getDomicilio().getNombre_calle() + " " + retrievedCliente2.getDomicilio().getNumero());

            Articulo retrievedArticulo1 = entityManager.find(Articulo.class, articulo1.getId());
            System.out.println("Artículo 1: Denominación: " + retrievedArticulo1.getDenominacion() + ", Cantidad: " + retrievedArticulo1.getCantidad() + ", Precio: " + retrievedArticulo1.getPrecio() + ", Categorías: " + retrievedArticulo1.getCategorias().stream().map(Categoria::getDenominacion).toList());

            Articulo retrievedArticulo2 = entityManager.find(Articulo.class, articulo2.getId());
            System.out.println("Artículo 2: Denominación: " + retrievedArticulo2.getDenominacion() + ", Cantidad: " + retrievedArticulo2.getCantidad() + ", Precio: " + retrievedArticulo2.getPrecio() + ", Categorías: " + retrievedArticulo2.getCategorias().stream().map(Categoria::getDenominacion).toList());

            Articulo retrievedArticulo3 = entityManager.find(Articulo.class, articulo3.getId());
            System.out.println("Artículo 3: Denominación: " + retrievedArticulo3.getDenominacion() + ", Cantidad: " + retrievedArticulo3.getCantidad() + ", Precio: " + retrievedArticulo3.getPrecio() + ", Categorías: " + retrievedArticulo3.getCategorias().stream().map(Categoria::getDenominacion).toList());

            Articulo retrievedArticulo4 = entityManager.find(Articulo.class, articulo4.getId());
            System.out.println("Artículo 4: Denominación: " + retrievedArticulo4.getDenominacion() + ", Cantidad: " + retrievedArticulo4.getCantidad() + ", Precio: " + retrievedArticulo4.getPrecio() + ", Categorías: " + retrievedArticulo4.getCategorias().stream().map(Categoria::getDenominacion).toList());

            Categoria retrievedCategoria1 = entityManager.find(Categoria.class, categoria1.getId());
            System.out.println("Categoría 1: Denominación: " + retrievedCategoria1.getDenominacion() + ", Artículos: " + retrievedCategoria1.getArticulos().stream().map(Articulo::getDenominacion).toList());

            Categoria retrievedCategoria2 = entityManager.find(Categoria.class, categoria2.getId());
            System.out.println("Categoría 2: Denominación: " + retrievedCategoria2.getDenominacion() + ", Artículos: " + retrievedCategoria2.getArticulos().stream().map(Articulo::getDenominacion).toList());

            DetalleFactura retrievedDF1 = entityManager.find(DetalleFactura.class, detalleFactura1.getId());
            System.out.println("Detalle Factura 1: Cantidad: " + retrievedDF1.getCantidad() + ", Subtotal: " + retrievedDF1.getSubtotal() + ", Factura: " + (retrievedDF1.getFactura() != null ? retrievedDF1.getFactura().getNumero() : "No Asociada"));

            DetalleFactura retrievedDF2 = entityManager.find(DetalleFactura.class, detalleFactura2.getId());
            System.out.println("Detalle Factura 2: Cantidad: " + retrievedDF2.getCantidad() + ", Subtotal: " + retrievedDF2.getSubtotal() + ", Factura: " + (retrievedDF2.getFactura() != null ? retrievedDF2.getFactura().getNumero() : "No Asociada"));

            DetalleFactura retrievedDF3 = entityManager.find(DetalleFactura.class, detalleFactura3.getId());
            System.out.println("Detalle Factura 3: Cantidad: " + retrievedDF3.getCantidad() + ", Subtotal: " + retrievedDF3.getSubtotal() + ", Factura: " + (retrievedDF3.getFactura() != null ? retrievedDF3.getFactura().getNumero() : "No Asociada"));

            DetalleFactura retrievedDF4 = entityManager.find(DetalleFactura.class, detalleFactura4.getId());
            System.out.println("Detalle Factura 4: Cantidad: " + retrievedDF4.getCantidad() + ", Subtotal: " + retrievedDF4.getSubtotal() + ", Factura: " + (retrievedDF4.getFactura() != null ? retrievedDF4.getFactura().getNumero() : "No Asociada"));

            Factura retrievedFactura1 = entityManager.find(Factura.class, factura1.getId());
            System.out.println("Factura 1: Número: " + retrievedFactura1.getNumero() + ", Fecha: " + retrievedFactura1.getFecha() + ", Precio: " + retrievedFactura1.getPrecio() + ", Cliente: " + retrievedFactura1.getCliente().getNombre() + " " + retrievedFactura1.getCliente().getApellido());

            Factura retrievedFactura2 = entityManager.find(Factura.class, factura2.getId());
            System.out.println("Factura 2: Número: " + retrievedFactura2.getNumero() + ", Fecha: " + retrievedFactura2.getFecha() + ", Precio: " + retrievedFactura2.getPrecio() + ", Cliente: " + retrievedFactura2.getCliente().getNombre() + " " + retrievedFactura2.getCliente().getApellido());

            Domicilio retrievedDomicilio1 = entityManager.find(Domicilio.class, domicilio1.getId());
            System.out.println("Domicilio 1: Calle: " + retrievedDomicilio1.getNombre_calle() + ", Número: " + retrievedDomicilio1.getNumero());

            Domicilio retrievedDomicilio2 = entityManager.find(Domicilio.class, domicilio2.getId());
            System.out.println("Domicilio 2: Calle: " + retrievedDomicilio2.getNombre_calle() + ", Número: " + retrievedDomicilio2.getNumero());

        } catch (Exception e) {
            // Manejo de errores: rollback de la transacción en caso de error
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Cerrar el EntityManager y el EntityManagerFactory para liberar recursos
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
