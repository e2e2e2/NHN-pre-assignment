package org.zerock.ex2.repository;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.test.annotation.Commit;
import org.zerock.ex2.entity.Memo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;


@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass(){
        System.out.println(memoRepository.getClass().getName());
    }


    @Disabled("....")
    @Test
    public void testInsertDummies(){

        IntStream.rangeClosed(1,100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });
    }


    @Disabled("....")
    @Test
    public void testSelect(){

        Long mno = 2L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("------------------------------");

        if(result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }

    }


    @Transactional
    @Disabled("....")
    @Test
    public void testSelect2(){

        Long mno = 2L;

        Memo memo = memoRepository.getOne(mno);

        System.out.println("------------------------------");

        System.out.println(memo);

    }


    @Disabled("....")
    @Test
    public void testUpdate(){
        Memo memo = Memo.builder().mno(3L).memoText("Update Test"). build();

        System.out.println(memoRepository.save(memo));
    }


    @Disabled("....")
    @Test
    public void testDelete() {
        Long mno = 1L;
        memoRepository.deleteById(mno);
    }


    @Disabled("....")
    @Test
    public void testPageDefault() {
        Pageable pageable = PageRequest.of(0,10);
        // 한 페이지에 표시할 tuple수가
        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println("-----------------------testPageDefault-------------------");
        System.out.println(result);

        System.out.println("-----------------------testPageDefault-------------------");
        System.out.println("total pages: "+ result.getTotalPages());
        System.out.println("total count: "+ result.getTotalElements());
        System.out.println("page number: "+ result.getNumber());
        System.out.println("page size: "+ result.getSize());
        System.out.println("has next page?: "+ result.hasNext());
        System.out.println("first page?: "+ result.isFirst());

        System.out.println("-----------------------testPageDefault-------------------");
        for (Memo memo : result.getContent()) {
            System.out.println(memo);
        }
    }


    @Disabled("....")
    @Test
    public void testSort() {
        Sort sort1 = Sort.by("memoText").ascending().and(Sort.by("mno").descending());

        Pageable pageable = PageRequest.of(0, 10, sort1);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Disabled("....")
    @Test
    public void testQueryMethods(){
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 75L);

        list.forEach(memo -> {
            System.out.println(memo);
        });
    }



    @Test
    public void testQueryMethodsWithPageable(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);

        result.get().forEach(memo -> System.out.println(memo));
    }

    @Commit//최종결과 커밋 위해 사용하는 어노테이션. 없으면 롤백 처리되어 결과 반영 안된다
    @Transactional//select로 우선 해당 엔티티 객체들을 가져오는 작업을 수행해야한다
    @Test
    public void testDeleteQueryMethods(){
        memoRepository.deleteMemoByMnoLessThan(20L);
        //SQL이용하듯이 한번에 삭제되는게 아니라 각 엔티티 객체를 한번에 삭제하는 방식으로 작동한다.
        //효율 위해 @Query 사용이 권장된다
    }
    
    
    @Test
    public void QueryUpdateMemo(){
        Memo memo1 = Memo.builder().mno(20L).memoText("updated!!!!!!!!").build();
        memoRepository.updateMemoText(memo1);
    }


    @Test
    public void QueryNativeMemo(){
        List<Object[]> memos = memoRepository.getNativeResult();
        System.out.println(memos);
    }
}
