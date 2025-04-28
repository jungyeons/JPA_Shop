//package jpabook.jpashop.tablecenter;
//
//import jpabook.jpashop.objectcenter.Team;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityTransaction;
//import javax.persistence.Persistence;
//
//public class JpaMain {
//    public static void main(String[] args) {
//    //emf는 로딩시점에 딱 하나만 !!
//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//    //일반적인 일을 할때마다 em탄생
//    EntityManager em = emf.createEntityManager();
//
//    EntityTransaction tx = em.getTransaction();
//        tx.begin();
//
//        try {
//        Team team = new Team();
//        team.setName("TeamA");
//        em.persist(team);
//
//        Member member = new Member();
//        member.setUsername("memberA");
//        member.setTeamId(team.getId());
//        em.persist(member);
//        //커밋을 해야 db에 인서트 쿼리 날라감
//        Member findMember = em.find(Member.class, member.getId());
//        Long findTeamId = findMember.getTeamId();
//        Team findTeam = em.find(Team.class, findTeamId);
//        tx.commit();
//    } catch (Exception e) {
//        tx.rollback();
//        e.printStackTrace();
//    } finally {
//        em.close();
//    }
//        emf.close();
//}
//}
