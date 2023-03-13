package gov.nih.nci.evs.reportwriter.core.model.sparql;

public class Bindings {
	private Property conceptCode;
	private Property conceptLabel;
	private Property property;
	private Property propertyLabel;
	private Property propertyCode;
	private Property propertyValue;
	private Property axiom;
	private Property axiomProperty;
	private Property axiomValue;
	private Property subclass;
	private Property subclassCode;
	private Property subclassLabel;
	private Property superclass;
	private Property superclassCode;
	private Property superclassLabel;
	private Property namedGraph;


////////////////////////////////////////////////////////////

	private Property sourceName;
	private Property sourceCode;
	private Property associationName;
	private Property targetName;
	private Property targetCode;

	////////////////////////////////////////////
	private Property supportedAssociationName;
	private Property supportedAssociationCode;

	public Property getSupportedAssociationName() {
		return supportedAssociationName;
	}
	public Property getSupportedAssociationCode() {
		return supportedAssociationCode;
	}
	public void setSupportedAssociationName(Property supportedAssociationName) {
		this.supportedAssociationName = supportedAssociationName;
	}
	public void setSupportedAssociationCode(Property supportedAssociationCode) {
		this.supportedAssociationCode = supportedAssociationCode;
	}

	private Property supportedRoleName;
	private Property supportedRoleCode;

	public Property getSupportedRoleName() {
		return supportedRoleName;
	}
	public Property getSupportedRoleCode() {
		return supportedRoleCode;
	}
	public void setSupportedRoleName(Property supportedRoleName) {
		this.supportedRoleName = supportedRoleName;
	}
	public void setSupportedRoleCode(Property supportedRoleCode) {
		this.supportedRoleCode = supportedRoleCode;
	}

	public Property getSourceName() {
		return sourceName;
	}
	public Property getSourceCode() {
		return sourceCode;
	}
	public Property getAssociationName() {
		return associationName;
	}
	public Property getTargetName() {
		return targetName;
	}
	public Property getTargetCode() {
		return targetCode;
	}

////////////////////////////////////////////

	public void setSourceName(Property sourceName) {
		this.sourceName = sourceName;
	}

	public void setSourceCode(Property sourceCode) {
		this.sourceCode = sourceCode;
	}

	public void setAssociationName(Property associationName) {
		this.associationName = associationName;
	}

	public void setTargetName(Property targetName) {
		this.targetName = targetName;
	}

	public void setTargetCode(Property targetCode) {
		this.targetCode = targetCode;
	}

////////////////////////////////////////////////////////////


	// Support for Version Info
	private Property version;
	private Property date;
	private Property comment;

	private Property graphName;

	private Property source;

	public Property getConceptCode() {
		return conceptCode;
	}

	public void setConceptCodel(Property conceptCode) {
		this.conceptCode = conceptCode;
	}

	public Property getConceptLabel() {
		return conceptLabel;
	}

	public void setConceptLabel(Property conceptLabel) {
		this.conceptLabel = conceptLabel;
	}

	public Property getPropertyValue ()
	{
		return propertyValue;
	}

	public void setPropertyValue (Property propertyValue)
	{
		this.propertyValue = propertyValue;
	}

	public Property getProperty ()
	{
		return property;
	}

	public void setProperty (Property property)
	{
		this.property = property;
	}

	public Property getPropertyLabel ()
	{
		return propertyLabel;
	}

	public void setPropertyLabel (Property propertyLabel)
	{
		this.propertyLabel = propertyLabel;
	}

	public Property getPropertyCode ()
	{
		return propertyCode;
	}

	public void setPropertyCode (Property propertyCode)
	{
		this.propertyCode = propertyCode;
	}

	public Property getAxiom() {
		return axiom;
	}

	public void setAxiom(Property axiom) {
		this.axiom = axiom;
	}

	public Property getAxiomProperty() {
		return axiomProperty;
	}

	public void setAxiomProperty(Property axiomProperty) {
		this.axiomProperty = axiomProperty;
	}

	public Property getAxiomValue() {
		return axiomValue;
	}

	public void setAxiomValue(Property axiomValue) {
		this.axiomValue = axiomValue;
	}


	public Property getSubclass() {
		return subclass;
	}

	public void setSubclass(Property subclass) {
		this.subclass = subclass;
	}

	public Property getSubclassCode() {
		return subclassCode;
	}

	public void setSubclassCode(Property subclassCode) {
		this.subclassCode = subclassCode;
	}

	public Property getSubclassLabel() {
		return subclassLabel;
	}

	public void setSubclassLabel(Property subclassLabel) {
		this.subclassLabel = subclassLabel;
	}

	public Property getSuperclass() {
		return superclass;
	}

	public void setSuperclass(Property superclass) {
		this.superclass = superclass;
	}


	public Property getSuperclassCode() {
		return superclassCode;
	}

	public void setSuperclassCode(Property superclassCode) {
		this.superclassCode = superclassCode;
	}

	public Property getSuperclassLabel() {
		return superclassLabel;
	}

	public void setSuperclassLabel(Property superclassLabel) {
		this.superclassLabel = superclassLabel;
	}

	public Property getVersion() {
		return version;
	}

	public void setVersion(Property version) {
		this.version = version;
	}

	public Property getDate() {
		return date;
	}

	public void setDate(Property date) {
		this.date = date;
	}

	public Property getComment() {
		return comment;
	}

	public void setComment(Property comment) {
		this.comment = comment;
	}

	public Property getNamedGraph() {
		return namedGraph;
	}

	public void setNamedGraph(Property namedGraph) {
		this.namedGraph = namedGraph;
	}

	public Property getGraphName() {
		return graphName;
	}

	public void setGraphName(Property graphName) {
		this.graphName = graphName;
	}

	public Property getSource() {
		return source;
	}

	public void setSource(Property source) {
		this.source = source;
	}

	@Override
	public String toString()
	{
		return "ClassPojo [propertyValue = "+propertyValue+", property = "+property+", propertyLabel = "+propertyLabel+", propertyCode = "+propertyCode+"]";
	}
}
