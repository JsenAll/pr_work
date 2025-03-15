package com.org.pr_work.service;


import org.junit.jupiter.api.Test;

class BDServiceTest {

    @Test
    void startDelDocTest() throws InterruptedException {
        BDService bdService = new BDService();
        bdService.startDelDoc(5);
    }

}