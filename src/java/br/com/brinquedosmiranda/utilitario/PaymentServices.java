/**
 * PaymentServices class - encapsulates PayPal payment integration functions.
 *
 * @author Nam Ha Minh
 * @copyright https://codeJava.net
 */
package br.com.brinquedosmiranda.utilitario;

import java.util.ArrayList;
import java.util.List;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PaymentServices {

    private static final String CLIENT_ID = "Ae4BQbfvr9kDK-2DAGIOFsMjp23NeOeyKzxCijwWwxTqwea_mub5iWVApSNYH1LK2Ln8TmhHh7z5bS7Z";
    private static final String CLIENT_SECRET = "ELGQcfv1bBrc8YVfV9SrAotoR7Sb8ghPYfFMDOJIFKHkrBnon5RFDgkX8hK2hKyvEKTGtr1EY1ioaMt3";
    private static final String MODE = "sandbox";

    public String authorizePayment(HttpServletRequest request)
            throws PayPalRESTException {

        Payer payer = getPayerInformation(request);
        RedirectUrls redirectUrls = getRedirectURLs();
        List<Transaction> listTransaction = getTransactionInformation(request);

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent("authorize");

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        Payment approvedPayment = requestPayment.create(apiContext);

        System.out.println("=== CREATED PAYMENT: ====");
        System.out.println(approvedPayment);

        return getApprovalLink(approvedPayment);

    }

    private Payer getPayerInformation(HttpServletRequest request) {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
     
        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName("Vagner")
                .setLastName("Ferreira")
                .setEmail("vagner@hotmail");

        payer.setPayerInfo(payerInfo);

        return payer;
    }

    private RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("index.jsp");
        redirectUrls.setReturnUrl("http://localhost:8084/brinquedos-aws/review_payment");

        return redirectUrls;
    }

    private List<Transaction> getTransactionInformation(HttpServletRequest request) {
      

        String total_carrinho = request.getParameter("totalcarrinho");;
        String Shipping = request.getParameter("shipping");
        String Tax = request.getParameter("tax");
        String subTotal = request.getParameter("subtotal");
        String nome = request.getParameter("produto");

        //------------ PEGAR O VALOR DO SUBTOTAL--------------------
        /*
        PedidoDAO pedidoDAO = new PedidoDAO();
        List<ItensPedido> itens = pedidoDAO.listarItens(request);
        for (ItensPedido i : itens) {
            nome = i.getNome();
            total_carrinho += i.getValor() * i.getQuantidade();
        }
          //------------ PEGAR O VALOR DO SUBTOTAL--------------------
         */
        Details details = new Details();
        details.setShipping("" + Shipping);
        details.setSubtotal("" + total_carrinho);
        details.setTax("" + Tax);

        Amount amount = new Amount();
        amount.setCurrency("BRL");
        amount.setTotal("" + total_carrinho);
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(nome);

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();
  //-------------------- ADICIONAR VARIOS ITENS----------------------
      //  for (ItensPedido i : itens) {
            Item item = new Item();
            item.setCurrency("BRL");
            item.setName(nome);
            item.setPrice("" + subTotal);
            item.setTax("" + Tax);
            item.setQuantity("" + 1);
            items.add(item);
      //  }
   //-------------------- ADICIONAR VARIOS ITENS----------------------
        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        return approvalLink;
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment payment = new Payment().setId(paymentId);

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        return payment.execute(apiContext, paymentExecution);
    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return Payment.get(apiContext, paymentId);
    }
}
