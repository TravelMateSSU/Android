package com.example.tourmate.dto;

import android.content.Context;

import com.example.tourmate.managers.DBManager;

import java.util.Map;

/**
 * Created by IronFactory on 2016. 10. 7..
 */
public class Tourist {

    public static final String PROPERTY_TITLE = "title";
    public static final String PROPERTY_CONTENT_ID = "contentid";
    public static final String PROPERTY_CONTENT_TYPE_ID = "contenttypeid";
    public static final String PROPERTY_IMAGE_1 = "firstimage";
    public static final String PROPERTY_IMAGE_2 = "firstimage2";
    public static final String PROPERTY_CATEGORY_1 = "cat1";
    public static final String PROPERTY_CATEGORY_2 = "cat2";
    public static final String PROPERTY_CATEGORY_3 = "cat3";
    public static final String PROPERTY_ADDRESS_1 = "addr1";
    public static final String PROPERTY_ADDRESS_2 = "addr2";
    public static final String PROPERTY_ZIP_CODE = "zipcode";
    public static final String PROPERTY_MAP_X = "mapX";
    public static final String PROPERTY_MAP_Y = "mapY";




    // 관광지 이름
    private String title;

    // 관광지 구분 ID
    private String contentId;

    // 관광지 타입 ID
    private String contentTypeId;

    // 대표 이미지 1
    private String titleImage1;

    // 대표 이미지 2
    private String titleImage2;

    // 주소
    private String address;

    // 기본 주소
    private String addr1;

    // 상세 주소
    private String addr2;

    // 우편 번호
    private String zipCode;

    // 대분류
    private CategoryDto category1;

    // 중분류
    private CategoryDto category2;

    // 소분류
    private CategoryDto category3;

    // x좌표
    private double x = 0.0;

    // y좌표
    private double y = 0.0;

    private Context context;


    public Tourist() {
    }

    public Tourist(Context context, Map<String, String> data) {
        this.context = context;
        DBManager dbManager = new DBManager(context);

        if (data.get(PROPERTY_TITLE) != null) {
            title = data.get(PROPERTY_TITLE);
        }

        if (data.get(PROPERTY_CONTENT_ID) != null) {
            contentId = data.get(PROPERTY_CONTENT_ID);
        }

        if (data.get(PROPERTY_CONTENT_TYPE_ID) != null) {
            contentTypeId = data.get(PROPERTY_CONTENT_TYPE_ID);
        }

        if (data.get(PROPERTY_IMAGE_1) != null) {
            titleImage1 = data.get(PROPERTY_IMAGE_1);
        }

        if (data.get(PROPERTY_IMAGE_2) != null) {
            titleImage2 = data.get(PROPERTY_IMAGE_2);
        }

        if (data.get(PROPERTY_ADDRESS_1) != null) {
            addr1 = data.get(PROPERTY_ADDRESS_1);
        }

        if (data.get(PROPERTY_ADDRESS_2) != null) {
            addr2 = data.get(PROPERTY_ADDRESS_2);
        }

        if (data.get(PROPERTY_ZIP_CODE) != null) {
            zipCode = data.get(PROPERTY_ZIP_CODE);
        }

        if (data.get(PROPERTY_CATEGORY_1) != null) {
            String category = data.get(PROPERTY_CATEGORY_1);
            category1 = new CategoryDto();
            category1.setCode(category);
            category1.setName(dbManager.getCategoryName(category));
        }

        if (data.get(PROPERTY_CATEGORY_2) != null) {
            String category = data.get(PROPERTY_CATEGORY_2);
            category2 = new CategoryDto();
            category2.setCode(category);
            category2.setName(dbManager.getCategoryName(category));
        }

        if (data.get(PROPERTY_CATEGORY_3) != null) {
            String category = data.get(PROPERTY_CATEGORY_3);
            category3 = new CategoryDto();
            category3.setCode(category);
            category3.setName(dbManager.getCategoryName(category));
        }

        if (data.get(PROPERTY_MAP_X) != null) {
            x = Double.valueOf(data.get(PROPERTY_MAP_X));
        }

        if (data.get(PROPERTY_MAP_Y) != null) {
            y = Double.valueOf(data.get(PROPERTY_MAP_Y));
        }
    }


    @Override
    public String toString() {
        return "title = " + title + ", contentId = " + contentId +
                ", contentTypeId = " + contentTypeId +
                ", titleImage1 = " + titleImage1 +
                ", titleImage2 = " + titleImage2 +
                ", address = " + getAddress() +
                ", category1 = " + category1.getName() +
                ", category2 = " + category2.getName() +
                ", category3 = " + category3.getName() +
                ", x = " + x + ", y = " + y;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(String contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public String getTitleImage1() {
        return titleImage1;
    }

    public void setTitleImage1(String titleImage1) {
        this.titleImage1 = titleImage1;
    }

    public String getTitleImage2() {
        return titleImage2;
    }

    public void setTitleImage2(String titleImage2) {
        this.titleImage2 = titleImage2;
    }

    public String getAddress() {
        return (addr1 == null ? "" : addr1) + (addr2 == null ? "" : addr2) + (zipCode == null ? "" : zipCode);
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public CategoryDto getCategory1() {
        return category1;
    }

    public void setCategory1(CategoryDto category1) {
        this.category1 = category1;
    }

    public CategoryDto getCategory2() {
        return category2;
    }

    public void setCategory2(CategoryDto category2) {
        this.category2 = category2;
    }

    public CategoryDto getCategory3() {
        return category3;
    }

    public void setCategory3(CategoryDto category3) {
        this.category3 = category3;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
