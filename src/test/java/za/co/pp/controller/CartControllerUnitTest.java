package za.co.pp.controller;

import javax.servlet.http.HttpSession;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import za.co.pp.PrettyParadiseClientApplication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {PrettyParadiseClientApplication.class})
@AutoConfigureMockMvc
class CartControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Given a request to store product with productId 1 in cart for the first time and another there after, " +
            "when /cart/1 endpoint hit twice successively, " +
            "then a cart for the session is created with size 1 and 2 after another call with the same session")
    void canUseSaveProductIdsInSameCart() throws Exception {
        MvcResult mvcResultFirstRequest = makeRequestToAddItemToCartAndAssertOkResponseWithSession(null);
        assertNumberOfItemsAddedToCart(mvcResultFirstRequest, 1);

        HttpSession sessionFirstRequest = mvcResultFirstRequest.getRequest().getSession();
        MvcResult mvcResultSecondRequest = makeRequestToAddItemToCartAndAssertOkResponseWithSession(sessionFirstRequest);
        assertNumberOfItemsAddedToCart(mvcResultSecondRequest, 2);
    }

    @Test
    @DisplayName("Given 2 independent requests to add products to a cart, " +
            "when /cart/1 and cart/1 endpoint is hit independently, " +
            "then a session exists independently for the 2 requests and a cart attribute exists for both of size 1")
    void canSaveProductIdsForIndependentSessions() throws Exception {
        MvcResult mvcResultFirstRequest = makeRequestToAddItemToCartAndAssertOkResponseWithSession(null);
        assertNumberOfItemsAddedToCart(mvcResultFirstRequest, 1);

        MvcResult mvcResultSecondIndependentRequest = makeRequestToAddItemToCartAndAssertOkResponseWithSession(null);
        assertNumberOfItemsAddedToCart(mvcResultSecondIndependentRequest, 1);

    }

    private void assertNumberOfItemsAddedToCart(final MvcResult mvcResultFirstRequest, int numberOfItems) {
        List<Long> cartItemsFirstRequest = getSessionCartAttribute(mvcResultFirstRequest);
        assertThat(cartItemsFirstRequest).isNotNull().hasSize(numberOfItems);
    }

    private MvcResult makeRequestToAddItemToCartAndAssertOkResponseWithSession(HttpSession httpSession) throws Exception {
        if (httpSession == null) {
            return this.mockMvc.perform(post("/cart/{productId}", 1))
                    .andExpect(status().isOk())
                    .andReturn();
        }
        return this.mockMvc.perform(post("/cart/{productId}", 1).session((MockHttpSession) httpSession))
                .andExpect(status().isOk())
                .andReturn();

    }

    private List<Long> getSessionCartAttribute(final MvcResult mvcResultFirstRequest) {
        HttpSession sessionFirstRequest = mvcResultFirstRequest.getRequest().getSession();
        return (List<Long>) sessionFirstRequest.getAttribute("cart");
    }

}
