
package com.dealalert.webapp.services;

import com.dealalert.webapp.models.Alert;
import com.dealalert.webapp.models.Item;
import com.dealalert.webapp.models.WishList;
import com.dealalert.webapp.repository.AlertRepository;
import com.dealalert.webapp.repository.ItemRepository;
import com.dealalert.webapp.repository.WishListRepository;
import com.dealalert.webapp.utils.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AlertService {


    private final WishListRepository wishListRepository;
    private final WishListService wishListService;
    private final ItemRepository itemRepository;
    private final AlertRepository alertRepository;


    @Autowired
    public AlertService(ItemRepository itemRepository, WishListRepository wishListRepository, WishListService wishListService, AlertRepository alertRepository) {
        this.itemRepository = itemRepository;
        this.wishListRepository = wishListRepository;
        this.wishListService = wishListService;
        this.alertRepository = alertRepository;

    }

    public void enableAlerts(String username, String email, String itemId, double alertPrice) {

        //helper to store alert status in a map
        WishList wishList = wishListService.readWishList(username);
        wishList.addToMap(itemId,true);
        wishListService.createWishlist(wishList);


            if (exists(username, itemId)) {
                Alert tmp = getAlert(username,itemId);
                tmp.setAlertPrice(alertPrice);
                alertRepository.save(tmp);
                initMonitoringOfPrice(alertPrice, username, email, itemId);

            } else{
                Alert alert = new Alert(username, email, itemId, alertPrice,true);
                alertRepository.save(alert);
                initMonitoringOfPrice(alertPrice, username, email, itemId);

            }


    }

    public void disableAlerts(String username,  String itemId) {

            alertRepository.delete(alertRepository.findByUsernameAndItemId(username, itemId));
            WishList wishList = wishListRepository.findByUsername(username);
            wishList.addToMap(itemId,false);
            wishListService.createWishlist(wishList);

    }

    public void initMonitoringOfPrice(double alertPrice, String username, String email, String itemId) {
        System.out.println("===> Monitoring price <===");
        WishList tmp = wishListRepository.findByUsername(username);
        Map<String, Boolean> itemIdMap = tmp.getItemIdMap();
        Optional<Item> optional = Optional.empty();
        for (String element : itemIdMap.keySet()) {
            if (element.contains(itemId)) {
                optional = itemRepository.findById(element);
            }
        }

            if (optional.isPresent()) {
                if (alertPrice >= optional.get().getPrice()) {
                    alertUser(username, email, itemId, optional.get().getPrice());
                }
            }


        }

        private void alertUser (String username, String email, String itemId, double realPrice){

            //alert user
            Optional<Item> item = itemRepository.findById(itemId);
            String name = item.get().getName();
            SendMail.sendMail(username, email, name, realPrice);
            // handle db data
            alertRepository.delete(alertRepository.findByUsernameAndItemId(username, itemId));
            WishList wishList = wishListRepository.findByUsername(username);
            wishList.addToMap(itemId,false);
            wishListService.createWishlist(wishList);

        }

        public Alert getAlert (String username, String itemId) {
        return alertRepository.findByUsernameAndItemId(username,itemId);
        }

        public boolean exists(String username, String itemId) {
        return alertRepository.existsByUsernameAndItemId(username, itemId);
        }

        public boolean existsByStatus(boolean status) {
        return alertRepository.existsByStatus(status);
        }

        public List<Alert> getAlertsByStatus(boolean status) {
        return alertRepository.findAllByStatus(status);
        }

}
