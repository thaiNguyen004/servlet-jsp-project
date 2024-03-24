package org.servlet.assignment.order.controller.home;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.servlet.assignment.catalog.Product;
import org.servlet.assignment.catalog.service.ProductService;
import org.servlet.assignment.order.ActionMustLoggedInException;
import org.servlet.assignment.order.CartItem;
import org.servlet.assignment.order.ShoppingSession;
import org.servlet.assignment.order.impl.CartDao;
import org.servlet.assignment.order.service.CartService;
import org.servlet.assignment.order.service.ShoppingSessionService;
import org.servlet.assignment.user.CustomPrincipal;
import org.servlet.assignment.user.User;
import org.servlet.assignment.user.service.UserService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;


@WebServlet(name = "CartController", value = {"/cart/add", "/my-cart", "/cart/update", "/cart/delete"})
public class CartController extends GenericServlet<CartItem> {

    private final CartService cartService = new CartService();
    private final ProductService productService = new ProductService();
    private final ShoppingSessionService shoppingSessionService = new ShoppingSessionService();
    private final UserService userService = new UserService();

    public CartController() {
        super(new CartDao());
    }

    @Override
    void processRequest(HttpServletRequest request, HttpServletResponse response, CartItem entity) throws IOException, ServletException {
        String uri = request.getRequestURI();
        if (uri.equals("/cart/add")) {
            response.sendRedirect("/my-cart");
        }
    }

    @Override
    void processDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.equals("/my-cart")) {
            try {
                CustomPrincipal principal = checkLogin(req);
                ShoppingSession shoppingSession = shoppingSessionService.findShoppingSessionByUsername(principal.getName());
                User user = userService.findByUsername(principal.getName());
                if (shoppingSession != null) {
                    req.setAttribute("cartItems", shoppingSession.getCartItems());
                }
                req.setAttribute("user", user);
                req.getRequestDispatcher("/page/home/my-carts.jsp").forward(req, resp);
            } catch (ActionMustLoggedInException ex) {
                resp.sendRedirect("/login");
            }
        }
    }

    @Override
    void handleErrors(HttpServletRequest req, HttpServletResponse resp,
                      Map<String, String> violations, CartItem entity)
            throws ServletException, IOException {

        String uri = req.getRequestURI();
        if (uri.equals("/cart/add")) {
            req.setAttribute("violations_cart", violations);
            req.setAttribute("product", entity.getProduct());
            req.getRequestDispatcher("/page/home/detail-product.jsp").forward(req, resp);
        }
    }

    @Override
    CartItem createEntity(HttpServletRequest request) throws InvocationTargetException, IllegalAccessException, ServletException, IOException {
        CustomPrincipal principal = checkLogin(request);
        User user = userService.findByUsername(principal.getName());
        String quantity = request.getParameter("quantity");
        Product product = Optional.ofNullable(
                productService.findById(Long.parseLong(request.getParameter("productId"))))
                .orElse(null);
        CartItem cartItemExist = cartService.findCartItemByProductId(product.getId());
        // check duplicate cart item with 1 product id
        if (cartItemExist != null) {
            // duplicate
            try {
                int quantityInt = Integer.parseInt(quantity);
                int newQuantity = Integer.parseInt(cartItemExist.getQuantity()) + quantityInt;
                cartItemExist.setQuantity(newQuantity + "");
            } catch (NumberFormatException ex) {
                cartItemExist.setQuantity(quantity);
                return cartItemExist;
            }
        } else {
            // no duplicate
            cartItemExist = new CartItem();
            cartItemExist.setProduct(product);
            cartItemExist.setQuantity(quantity);
        }

        // check to see if the user has created cart?
        ShoppingSession shoppingSession = shoppingSessionService
                .findShoppingSessionByUsername(principal.getName());
        if (shoppingSession == null) {
            // never created before
            shoppingSession = new ShoppingSession();
            shoppingSession.setUser(user);
            shoppingSessionService.createShoppingSession(shoppingSession);
        }
        cartItemExist.setShoppingSession(shoppingSessionService.findShoppingSessionByUsername(principal.getName()));
        return cartItemExist;
    }


    public CustomPrincipal checkLogin(HttpServletRequest request) throws ServletException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            throw new ActionMustLoggedInException("You must login to access this page");
        }
        return (CustomPrincipal) session.getAttribute("user");
    }
}
