package se.yrgo.services.calls;

import se.yrgo.domain.Call;
import se.yrgo.domain.Action;
import org.springframework.beans.factory.annotation.Autowired;
import se.yrgo.services.customers.CustomerManagementService;
import se.yrgo.services.customers.CustomerNotFoundException;
import se.yrgo.services.diary.DiaryManagementService;

public abstract class CallHandlingServiceImpl implements CallHandlingService {
    private CustomerManagementService customerService;
    private DiaryManagementService diaryService;

    @Autowired
    public CallHandlingServiceImpl(CustomerManagementService customerService,
                                   DiaryManagementService diaryService) {
        this.customerService = customerService;
        this.diaryService = diaryService;
    }

    @Override
    public void recordCall(String customerId, Call callDetails, Action action) {
        // Step 1: Record the call in CustomerManagementService
        try {
            customerService.recordCall(customerId, callDetails);
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Step 2: Record any action in DiaryManagementService
        if (action != null) {
            diaryService.recordAction(action);
        }
    }
}