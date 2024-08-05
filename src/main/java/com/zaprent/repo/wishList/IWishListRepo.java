package com.zaprent.repo.wishList;

import com.zaprnt.beans.models.WishList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWishListRepo extends MongoRepository<WishList, String>, CustomWishListRepository {
}
