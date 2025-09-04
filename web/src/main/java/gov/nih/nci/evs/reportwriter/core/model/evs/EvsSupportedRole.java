package gov.nih.nci.evs.reportwriter.core.model.evs;

import java.io.*;
import java.util.*;
import java.net.*;

public class EvsSupportedRole
{

// Variable declaration
	private String name;
	private String code;

// Default constructor
	public EvsSupportedRole() {
	}

// Constructor
	public EvsSupportedRole(
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
