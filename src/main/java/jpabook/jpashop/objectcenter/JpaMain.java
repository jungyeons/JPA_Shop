package jpabook.jpashop.objectcenter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
            // 4. 저장 작업
            // (1) Team 엔티티 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);  // TeamA 저장

            // (2) Member 엔티티 저장
            Member member = new Member();
            member.setUsername("memberA");
            member.setTeam(team);  // memberA의 팀을 TeamA로 설정
            em.persist(member);  // memberA 저장

            // 5. 조회 작업
            // (1) memberA를 DB에서 조회
            Member findMember = em.find(Member.class, member.getId());

            // 6. 수정 작업
            // (2) TeamA가 아닌 새로운 Team을 찾아서 memberA에 설정
            Team newTeam = em.find(Team.class, 100L);  // 예시로 ID 100인 Team 조회
            findMember.setTeam(newTeam);  // memberA의 팀을 새로운 Team으로 변경

            // 7. 결과 출력
            Team findTeam = findMember.getTeam();
            System.out.println("findTeam = " + findTeam.getName());  // 새로운 팀 이름 출력

            // 8. 커밋: 트랜잭션을 성공적으로 완료
            tx.commit();
        } catch (Exception e) {
            // 예외 발생 시 트랜잭션 롤백
            tx.rollback();
            e.printStackTrace();
        } finally {
            // 9. 리소스 정리: EntityManager와 EntityManagerFactory를 종료
            em.close();
            emf.close();
        }
    }
}
