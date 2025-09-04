package gov.nih.nci.evs.reportwriter.core.model.evs;

import java.io.*;
import java.util.*;
import java.net.*;

public class EvsAssociation
{

// Variable declaration
	private String sourceName;
	private String sourceCode;
	private String associationName;
	private String targetName;
	private String targetCode;

// Default constructor
	public EvsAssociation() {
	}

// Constructor
	public EvsAssociation(
		String sourceName,
		String sourceCode,
		String associationName,
		String targetName,
		String targetCode) {

		this.sourceName = sourceName;
		this.sourceCode = sourceCode;
		this.associationName = associationName;
		this.targetName = targetName;
		this.targetCode = targetCode;
	}

// Set methods
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public void setAssociationName(String associationName) {
		this.associationName = associationName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}


// Get methods
	public String getSourceName() {
		return this.sourceName;
	}

	public String getSourceCode() {
		return this.sourceCode;
	}

	public String getAssociationName() {
		return this.associationName;
	}

	public String getTargetName() {
		return this.targetName;
	}

	public String getTargetCode() {
		return this.targetCode;
	}
}
