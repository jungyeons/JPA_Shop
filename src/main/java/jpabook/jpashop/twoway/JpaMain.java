package jpabook.jpashop.twoway;

import javax.persistence.*;
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
            // 4. 저장 작업
            // (1) Team 엔티티 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);  // TeamA 저장

            // (2) Member 엔티티 저장
            Member member = new Member();
            member.setUsername("memberA");
            member.setTeam(team);  // 팀에 매핑
            //위 코드를 날리고
            //Team에서 이걸 넣어주는 방법도 있다.
//
//            public void addMember(Member member) {
//                member.setTeam(this);
//                members.add(member);
//            }
            em.persist(member);  // memberA 저장

            // (3) 반대편에서도 관계 설정
            team.getMembers().add(member); // Team 엔티티의 members 리스트에 member 추가
            //위 코드를 날리고
            //MEMBER에서 이걸 넣어주는 방법도 있다.
//            public void changeTeam(Team team) {
//
//                this.team = team;
//                //양방향을 위한 메소드
//                team.getMembers().add(this);
//            }
            em.flush(); // 즉시 DB에 반영
            em.clear(); // 영속성 컨텍스트 초기화

            // 5. 조회 작업
            // (1) memberA를 DB에서 조회
            Member findMember = em.find(Member.class, member.getId());

            // (2) Team의 members 리스트에서 모든 Member 출력
            List<Member> members = findMember.getTeam().getMembers();
            for (Member m : members) {
                System.out.println(m.getUsername()); // memberA 출력
            }

            // 트랜잭션 커밋
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
