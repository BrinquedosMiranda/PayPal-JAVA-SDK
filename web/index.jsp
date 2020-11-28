<%-- 
    Document   : index
    Created on : 28/11/2020, 09:44:51
    Author     : conta
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PayPal</title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/all.css" integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt" crossorigin="anonymous">
    </head>
    <body>
        <div class="container">
            <h2 class="text-center">PayPal</h2>
            <div class="row justify-content-center">
                <div class="col-12 col-md-8 col-lg-6 pb-5">


                    <!--Form with header-->

                    <form action="payment-paypal" method="post">
                        <div class="card border-primary rounded-0">
                            <div class="card-header p-0">
                                <div class="bg-info text-white text-center py-2">
                                    <h3><i class="fab fa-cc-paypal"></i> Pagamento</h3>
                                </div>
                            </div>
                            <div class="card-body p-3">

                                <!--Body-->
                                <div class="form-group">
                                     <div class="form-group col-md-12">
                                        <label for="categoria">Produto *</label>
                                        <input type="text" class="form-control" id="produto" name="produto" placeholder="Produto" value="Produto Teste" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="form-group col-md-12">
                                         <label for="categoria">Subtotal *</label>
                                        <input type="text" class="form-control" id="subtotal" name="subtotal" placeholder="Subtotal" value="100" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                   <div class="form-group col-md-12">
                                         <label for="categoria">Shipping *</label>
                                        <input type="text" class="form-control" id="shipping" name="shipping" placeholder="Shipping" value="0" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                   <div class="form-group col-md-12">
                                         <label for="categoria">Tax *</label>
                                        <input type="text" class="form-control" id="tax" name="tax" placeholder="Tax" value="0" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                   <div class="form-group col-md-12">
                                         <label for="categoria">Total Carrinho *</label>
                                        <input type="text" class="form-control" id="tax" name="totalcarrinho" placeholder="totalcarrinho" value="100" required>
                                    </div>
                                </div>


                                <div class="text-center">
                                    <input type="submit" value="Enviar" class="btn btn-info btn-block rounded-0 py-2">
                                </div>
                            </div>

                        </div>
                    </form>
                    <!--Form with header-->


                </div>
            </div>
        </div>
    </body>
</html>
