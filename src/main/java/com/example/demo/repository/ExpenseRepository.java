package com.example.demo.repository;

import com.example.demo.entity.Expense;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	List<Expense> findByUser(User user);
	
	@Query(value = "SELECT SUM(amount) FROM expenses WHERE user_id = :userId AND DATE_TRUNC(CASE WHEN :period = 'day' THEN 'day' WHEN :period = 'week' THEN 'week' WHEN :period = 'month' THEN 'month' ELSE 'day' END, date) = DATE_TRUNC(CASE WHEN :period = 'day' THEN 'day' WHEN :period = 'week' THEN 'week' WHEN :period = 'month' THEN 'month' ELSE 'day' END, CURRENT_DATE)", nativeQuery = true)
	Double getTotalExpenseByUserAndPeriod(@Param("userId") Long userId, @Param("period") String period);
}
