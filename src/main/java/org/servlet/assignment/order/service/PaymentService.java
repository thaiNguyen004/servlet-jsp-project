package org.servlet.assignment.order.service;

import org.servlet.assignment.order.Payment;
import org.servlet.assignment.order.impl.PaymentDao;

public class PaymentService {

    private final PaymentDao paymentDao = new PaymentDao();

    public void createPayment(Payment payment) {
        paymentDao.save(payment);
    }
}
