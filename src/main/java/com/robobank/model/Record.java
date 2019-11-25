package com.robobank.model;

import javax.xml.bind.annotation.XmlAttribute;


public class Record {
	
	private int transactionReference;
	private String accountNumber;
	private double startBalance;
	private double mutation;
	private String description;
	private double endBalance;
	
	@XmlAttribute(name="reference")
	public int getTransactionReference() {
		return transactionReference;
	}
	public void setTransactionReference(int transactionReference) {
		this.transactionReference = transactionReference;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public double getStartBalance() {
		return startBalance;
	}
	public void setStartBalance(double startBalance) {
		this.startBalance = startBalance;
	}
	public double getMutation() {
		return mutation;
	}
	public void setMutation(double mutation) {
		this.mutation = mutation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getEndBalance() {
		return endBalance;
	}
	public void setEndBalance(double endBalance) {
		this.endBalance = endBalance;
	}
	@Override
	public String toString() {
		return "Record [transactionReference=" + transactionReference + ", accountNumber=" + accountNumber
				+ ", startBalance=" + startBalance + ", mutation=" + mutation + ", description=" + description
				+ ", endBalance=" + endBalance + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + transactionReference;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Record other = (Record) obj;
		if (transactionReference != other.transactionReference)
			return false;
		return true;
	}
	
	

}
