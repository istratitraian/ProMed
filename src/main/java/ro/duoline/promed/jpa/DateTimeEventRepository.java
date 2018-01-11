/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.jpa;

import java.util.Collection;
import java.util.Date;
import org.springframework.data.repository.CrudRepository;
import ro.duoline.promed.domains.DayTimeEvent;

/**
 *
 * @author I.T.W764
 */
public interface DateTimeEventRepository extends CrudRepository<DayTimeEvent, Integer> {

//    public List<DayTimeEvent> findByUserId(Integer id);
//   @Query("SELECT U.name FROM User U WHERE LOWER(U.name) LIKE LOWER(concat(?1, '%'))")
//    @Query("SELECT FROM Event E WHERE EVENT_BEGIN LIKE '%?%')")
//    public List<DayTimeEvent> findByEventBegin(Date date);
//    public List<DayTimeEvent> findByStartDateLike(Date date);
//    public List<DayTimeEvent> findByUserIdAndStarDatetLike(Integer userId, Date date);
//    public List<DayTimeEvent> findWhereEventBeginLike(String date);

//    public Collection<? extends DayTimeEvent> findByUserIdAndStartGreaterThanEqual(Integer id, String start);
//    public Collection<? extends DayTimeEvent> findTop10ByUserIdAndStartGreaterThanEqual(Integer id, String start);
//    public Collection<? extends DayTimeEvent> findByUserIdAndStartGreaterThanEqualAndEndLessThanEqual(Integer id, String start,String end);
    public Collection<? extends DayTimeEvent> findByUserIdAndStartDateBetween(Integer id, Date start,Date end);
//    public Collection<? extends DayTimeEvent> findByUserIdAndStartDBetween(Integer id, String start,String end);
//    public Collection<? extends DayTimeEvent> findByUserIdAndStartLikeOrEndLike(Integer id, String start, String end);
//    public Collection<? extends DayTimeEvent> findBetweenByUserIdAndStartLikeAndEndLike(Integer id, String start, String end);
//    public Collection<? extends DayTimeEvent> findTop6ByUserIdAndStartLike(Integer id, String start);
//    public Collection<? extends DayTimeEvent> findByUserIdAndStartLikeLimit(Integer id, String start, Integer limit);

//    public Collection<? extends DayTimeEvent> findByUserIdAndStartGreaterThanEqualAndEndLesThanEqual(Integer id, String start, String end);


}
