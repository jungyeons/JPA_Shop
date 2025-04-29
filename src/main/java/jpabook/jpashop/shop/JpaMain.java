package jpabook.jpashop.shop;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        // 1. EntityManagerFactory 생성 (애플리케이션 시작 시 한 번만 생성)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 2. EntityManager 생성 (각 작업마다 EntityManager 생성)
        EntityManager em = emf.createEntityManager();

        // 3. 트랜잭션 시작
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Order order = new Order();
            order.addOrderItem(new OrderItem());

            tx.commit();
        } catch (Exception e) {
            // 예외 발생 시 트랜잭션 롤백
            tx.rollback();
            e.printStackTrace();
        } finally {
            // 6. 리소스 정리: EntityManager와 EntityManagerFactory 종료
            em.close();
            emf.close();
        }
    }
}

