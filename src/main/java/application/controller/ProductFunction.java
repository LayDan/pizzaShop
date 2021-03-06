package application.controller;

import application.domain.LocaleMessage;
import application.domain.Product;
import application.domain.TypeProduct;
import application.repository.ProductRepository;
import application.repository.TypeProductRepository;
import application.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Optional;


@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class ProductFunction {

    private TypeProductRepository typeProductRepository;
    private ProductRepository productRepository;
    private IProductService iproductService;

    public ProductFunction(TypeProductRepository typeProductRepository, ProductRepository productRepository, IProductService iproductService) {
        this.typeProductRepository = typeProductRepository;
        this.productRepository = productRepository;
        this.iproductService = iproductService;
    }

    private ArrayList<Integer> arr = new ArrayList<>();
    private String catalog = "catalog";
    private String allProduct = "AllProduct";
    private String types = "types";

    @GetMapping("/addProduct")
    public String addProduct(Model model, @RequestParam(value = "size", defaultValue = "1") String size, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        if (arr.size() != Integer.parseInt(size)) {
            arr.clear();
            for (int i = 1; i < Integer.parseInt(size) + 1; i++) {
                arr.add(i);
            }
        }
        model.addAttribute("array", arr);
        model.addAttribute(types, typeProductRepository.findAll());
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String getAddProduct(Model model,
                                @Valid Product product,
                                @RequestParam("file") MultipartFile file,
                                @RequestParam(name = "sizeProduct") String[] sizeProduct,
                                @RequestParam(name = "priceForProduct") Double[] price, Locale locale) throws IOException {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        LinkedHashMap<String, Double> map = new LinkedHashMap<>();

        if (sizeProduct.length == price.length) {
            for (int i = 0; i < sizeProduct.length; i++) {
                if (sizeProduct[i] == null || price[i] == null) {
                    break;
                } else {
                    map.put(sizeProduct[i], price[i]);
                }
            }
            Optional<TypeProduct> byType = typeProductRepository.findByType(product.getType().getType());
            if (byType.isPresent()) {
                Product product1 = Product.builder()
                        .code(product.getCode())
                        .description(product.getDescription())
                        .name(product.getName())
                        .priceFromSize(map)
                        .type(byType.get())
                        .build();
                iproductService.addProduct(product1, file);
            }
            model.addAttribute(allProduct, productRepository.findAll());
            model.addAttribute(types, typeProductRepository.findAllType());
            arr.clear();
            return catalog;
        } else {
            arr.clear();
            return "addProduct";
        }
    }

    @GetMapping("/editProduct")
    public String editProduct(Model model, Product product, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        model.addAttribute(product);
        return "editProduct";
    }

    @PostMapping("/editProduct")
    public String postEditProduct(Model model, @RequestParam(name = "productId") Product product, Double stock, String name, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        iproductService.editProduct(product, stock, name);
        model.addAttribute(allProduct, productRepository.findAll());
        model.addAttribute(types, typeProductRepository.findAllType());
        return catalog;
    }

    @GetMapping("/delete")
    public String deleteProduct(Model model, @RequestParam(name = "productId") Product product, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        iproductService.deleteProduct(product.getId());
        model.addAttribute(allProduct, productRepository.findAll());
        model.addAttribute(types, typeProductRepository.findAllType());
        return catalog;
    }

}
