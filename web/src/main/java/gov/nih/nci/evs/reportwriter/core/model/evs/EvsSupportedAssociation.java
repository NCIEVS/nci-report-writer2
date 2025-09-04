package gov.nih.nci.evs.reportwriter.core.model.evs;

import java.io.*;
import java.util.*;
import java.net.*;

public class EvsSupportedAssociation
{

// Variable declaration
	private String name;
	private String code;

// Default constructor
	public EvsSupportedAssociation() {
	}

// Constructor
	public EvsSupportedAssociation(
		String name,
		String code) {

		this.name = name;
		this.code = code;
	}

// Set methods
	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}

// Get methods
	public String getName() {
		return this.name;
	}

	public String getCode() {
		return this.code;
	}
}
