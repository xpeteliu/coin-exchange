package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.Market;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.UserFavoriteMarketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userFavorites")
@Api(tags = {"Controller for ops on userFavoriteMarket"})
public class UserFavoriteMarketController {

    @Autowired
    UserFavoriteMarketService userFavoriteMarketService;

    @PostMapping("/addFavorite")
    @ApiOperation("Add a market to My Favorite List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userFavoriteMarket", value = "JSON object representing userFavoriteMarket"),
    })
    public R UpdateStatus(@RequestBody Market userFavoriteMarket) {
        try {
            userFavoriteMarketService.add(userFavoriteMarket);
            return R.success("User Favorite Market successfully added");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
    }

    @DeleteMapping("/{symbol}")
    @ApiOperation("Delete a market from My Favorite List")
    public R UpdateStatus(@PathVariable String symbol) {
        try {
            userFavoriteMarketService.delete(symbol);
            return R.success("User Favorite Market successfully removed");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
    }

}
