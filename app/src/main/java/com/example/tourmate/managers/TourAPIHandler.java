package com.example.tourmate.managers;

import com.example.tourmate.dto.Tourist;

import java.util.List;

/**
 * Created by IronFactory on 2016. 10. 7..
 */
public interface TourAPIHandler {
    void searchById(Tourist tourist);
    void searchByIdFailed();

    void searchByKeyword(List<Tourist> touristList);
    void searchByKeywordFailed();
}
