package kube.demo.spring.data.mongodb.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "MONGO_COLLECTION")
public class StockData {
	@Id
	public ObjectId id;

	public Double Open;
	public Double High;
	public Double Low;
	public Double Close;
	public Double AdjClose;
	public Integer Volume;
	public String StockName;
	
	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}
	/**
	 * @return the open
	 */
	public Double getOpen() {
		return Open;
	}
	/**
	 * @param open the open to set
	 */
	public void setOpen(Double open) {
		Open = open;
	}
	/**
	 * @return the high
	 */
	public Double getHigh() {
		return High;
	}
	/**
	 * @param high the high to set
	 */
	public void setHigh(Double high) {
		High = high;
	}
	/**
	 * @return the low
	 */
	public Double getLow() {
		return Low;
	}
	/**
	 * @param low the low to set
	 */
	public void setLow(Double low) {
		Low = low;
	}
	/**
	 * @return the close
	 */
	public Double getClose() {
		return Close;
	}
	/**
	 * @param close the close to set
	 */
	public void setClose(Double close) {
		Close = close;
	}
	/**
	 * @return the adjClose
	 */
	public Double getAdjClose() {
		return AdjClose;
	}
	/**
	 * @param adjClose the adjClose to set
	 */
	public void setAdjClose(Double adjClose) {
		AdjClose = adjClose;
	}
	/**
	 * @return the volume
	 */
	public Integer getVolume() {
		return Volume;
	}
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(Integer volume) {
		Volume = volume;
	}
	/**
	 * @return the stockName
	 */
	public String getStockName() {
		return StockName;
	}
	/**
	 * @param stockName the stockName to set
	 */
	public void setStockName(String stockName) {
		StockName = stockName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StockData [id=" + id + ", Open=" + Open + ", High=" + High + ", Low=" + Low + ", Close=" + Close
				+ ", AdjClose=" + AdjClose + ", Volume=" + Volume + ", StockName=" + StockName + "]";
	}
	
	
}
