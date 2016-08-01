class ReportColumn:
    '''
    Class for modeling a ReportWriter templates. Contains the information
    for a ReportWriter Column.
    '''

    def __init__(self,
                 column_number = None,
                 label = None,
                 field_id = None,
                 property_type = None,
                 property_name = None,
                 is_preferred = None,
                 representational_form = None,
                 source = None,
                 qualifier_name = None,
                 qualifier_value = None,
                 delimiter = None,
                 conditional_column_id = None):
        self._column_number = column_number
        self._label = label
        self._field_id = field_id
        self._property_type = property_type
        self._is_preferred = is_preferred
        self._representational_form = representational_form
        self._source = source
        self._qualifier_name = qualifier_name
        self._qualifier_value = qualifier_value
        self._delimiter = delimiter
        self._conditional_column_id = conditional_column_id

    @property
    def column_number(self):
        return self._column_number

    @column_number.setter
    def column_number(self,value):
        self._column_number = value

    @property
    def label(self):
        return self._label

    @label.setter
    def label(self,value):
        self._label = value

    @property
    def field_id(self):
        return self._field_id

    @field_id.setter
    def field_id(self,value):
        self._field_id = value

    @property
    def property_type(self):
        return self._property_type

    @property_type.setter
    def property_type(self,value):
        self._property_type = value

    @property
    def property_name(self):
        return self._property_name

    @property_name.setter
    def property_name(self,value):
        self._property_name = value

    @property
    def is_preferred(self):
        return self._is_preferred

    @is_preferred.setter
    def is_preferred(self,value):
        self._is_preferred = value

    @property
    def representational_form(self):
        return self._representational_form

    @representational_form.setter
    def representational_form(self,value):
        self._representational_form = value

    @property
    def source(self):
        return self._source

    @source.setter
    def source(self,value):
        self._source = value

    @property
    def qualifier_name(self):
        return self._qualifier_name

    @qualifier_name.setter
    def qualifier_name(self,value):
        self._qualifier_name = value

    @property
    def delimiter(self):
        return self._delimiter

    @delimiter.setter
    def delimiter(self,value):
        self._delimiter = value

    @property
    def conditional_column_id(self):
        return self._conditional_column_id

    @conditional_column_id.setter
    def conditional_column_id(self,value):
        self._conditional_column_id = value

    def __str__(self):
        lines = []
        lines.append("column_number: " + self._column_number)
        lines.append("label: " + self._label)
        lines.append("field_id: " + self._field_id)
        lines.append("property_type: " + self._property_type)
        lines.append("property_name: " + self._property_name)
        lines.append("is_preferred: " + self._is_preferred)
        lines.append("representational_form: " + self._representational_form)
        lines.append("source: " + self._source)
        lines.append("qualifier_name: " + self._qualifier_name)
        lines.append("qualifier_value: " + self._qualifier_value)
        lines.append("delimiter: " + self._delimiter)
        lines.append("conditional_column_id: " + self._conditional_column_id)
        return "\n".join(lines)
