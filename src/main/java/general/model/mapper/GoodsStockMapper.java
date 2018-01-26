package general.model.mapper;

import general.model.GoodsStock;

public interface GoodsStockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsStock record);

    int insertSelective(GoodsStock record);

    GoodsStock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsStock record);

    int updateByPrimaryKey(GoodsStock record);
}