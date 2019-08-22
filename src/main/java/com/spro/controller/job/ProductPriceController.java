package com.spro.controller.job;

import com.spro.service.job.ProductPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/productPriceController")
@RestController
public class ProductPriceController {
    private Logger logger = LoggerFactory.getLogger(ProductPriceController.class);

    @Autowired
    private ProductPriceService productPriceService;


    @RequestMapping(value = "/queryPriceByProxyLevelAndProduct",method = RequestMethod.GET)
    public Map<String,Object> queryPriceByProxyLevelAndProduct(@RequestParam("proxyLevelId") Integer proxyLevelId,
                                                               @RequestParam("productId") Integer productId){
        return productPriceService.queryPriceByProxyLevelAndProduct(proxyLevelId,productId);
    }
}
