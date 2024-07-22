package com.example.demo.service;

import com.example.demo.dto.ExpenseUpdateDTO;
import com.example.demo.dto.ResponseWrapper;
import com.example.demo.entity.Expense;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ExpenseRepository;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ExpenseService {
    private static final Logger log = LoggerFactory.getLogger(ExpenseService.class);
    @Autowired
    private ExpenseRepository expenseRepository;
    
    @Autowired
    private UserRepository userRepository;

    public ResponseWrapper<Expense> createExpense(Expense expense, User user) {
        if (expense.getDate() == null) {
            expense.setDate(new Date());
        }
        expense.setUser(user);
        Expense createdExpense = expenseRepository.save(expense);
        return new ResponseWrapper<>(true, "Expense created successfully", createdExpense);
    }

    public ResponseWrapper<List<Expense>> getExpenseByUser(User user) {
        List<Expense> expenses = expenseRepository.findByUser(user);
        return new ResponseWrapper<>(true, "Expenses retrieved successfully", expenses);
    }

    public ResponseWrapper<Expense> getExpenseById(Long id, User user) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
        if (expense != null && expense.getUser().getId().equals(user.getId())) {
            return new ResponseWrapper<>(true, "Expense retrieved successfully", expense);
        }
        return new ResponseWrapper<>(false, "Expense not found", expense);
    }

    public ResponseWrapper<Expense> updateExpense(Long Id, ExpenseUpdateDTO updateExpense, User user) {
        Expense existingExpense = expenseRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
        if (existingExpense.getUser().getId().equals(user.getId())) {
            existingExpense.setDescription(updateExpense.getDescription());
            existingExpense.setAmount(updateExpense.getAmount());
            existingExpense.setDate(updateExpense.getDate() != null ? updateExpense.getDate() : new Date());
            Expense updatedExpense = expenseRepository.save(existingExpense);
            return new ResponseWrapper<>(true, "Expense updated successfully", updatedExpense);
        }
        return new ResponseWrapper<>(false, "Expense not found", existingExpense);
    }

    public ResponseWrapper<Expense> deleteExpense(Long id, User user) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
        if (expense != null && expense.getUser().getId().equals(user.getId())) {
            expenseRepository.delete(expense);
            return new ResponseWrapper<>(true, "Expense deleted successfully", expense);
        }
        return new ResponseWrapper<>(false, "Expense not found", expense);
    }
}
