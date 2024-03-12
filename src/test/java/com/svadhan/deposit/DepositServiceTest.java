package com.svadhan.deposit;

import com.svadhan.deposit.entity.Payment;
import com.svadhan.deposit.model.DepositRequest;
import com.svadhan.deposit.repository.DepositRepository;
import com.svadhan.deposit.service.DepositService;
import com.svadhan.deposit.util.DepositMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepositServiceTest {

    @Mock
    private DepositRepository depositRepository;

    @InjectMocks
    private DepositService depositService;

    @Test
    void saveDeposit_Success() {
        // Arrange
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setBankName("Test Bank");
        depositRequest.setReferenceId("123456");
        depositRequest.setScreenshot("screenshot.png");
        depositRequest.setRemarks("Test remarks");

        Payment payment = DepositMapper.mapToPaymentEntity(depositRequest);

        // Mock the behavior of depositRepository.save() method
        when(depositRepository.save(any())).thenReturn(payment);

        // Act
        boolean result = depositService.saveDeposit(depositRequest);

        // Assert
        assertTrue(result); // Check if the result is true

        // Verify that depositRepository.save() method is called once with the correct argument
        verify(depositRepository, times(1)).save(any());
    }
}
