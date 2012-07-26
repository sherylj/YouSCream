package edu.usc.apartment;

//IReportBack.java

public interface IReportBack
{
	public void reportBack(String tag, String message);
	public void reportTransient(String tag, String message);
}