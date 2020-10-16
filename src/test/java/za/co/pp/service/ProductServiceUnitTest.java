package za.co.pp.service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static za.co.pp.utils.ProductUtils.createDatabaseAndPopulateProductsTable;

@SpringBootTest
class ProductServiceUnitTest {

    @MockBean
    private JavaMailSender javaMailSender;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> simpleMailMessageArgumentCaptor;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ProductService productService;

    @Test
    void canGetExpectedEmailSubjectRecipientAndBody() throws Exception {
        createDatabaseAndPopulateProductsTable(dataSource);
        doNothing().when(javaMailSender).send(mimeMessage -> {});
        List<Long> cart = new ArrayList<>();
        cart.add(1L);
        cart.add(2L);
        cart.add(3L);

        this.productService.sendEmailToRequestProductsInCart(cart, "someemailaddress@gmail.com");

        Mockito.verify(javaMailSender).send(simpleMailMessageArgumentCaptor.capture());
        SimpleMailMessage simpleMailMessage = simpleMailMessageArgumentCaptor.getValue();
        assertThat(simpleMailMessage.getFrom()).isEqualToIgnoringCase("noreplyprettyparadise@gmail.com");
        assertThat(simpleMailMessage.getSubject()).isEqualToIgnoringCase("New Order");
        assertThat(simpleMailMessage.getText()).isEqualToIgnoringCase(
                "1 - pink and pretty - R20.0\n" +
                "2 - purple and popping - R20.0\n" +
                "3 - gray and glitter - R20.0\n"+
                "\n" +
                "someemailaddress@gmail.com placed the order."
        );
    }

}
