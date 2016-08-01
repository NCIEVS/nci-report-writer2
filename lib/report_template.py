class ReportTemplate:
    '''
    Class for modeling a ReportWriter templates. Contains the top level
    information for a ReportWriter templates, plus the individual column
    information.
    '''

    def __init__(self,
                 label = None,
                 root_concept_code = None,
                 coding_scheme_name = None,
                 coding_scheme_version = None,
                 association_name = None,
                 direction = None,
                 level = None,
                 delimiter = None):
        self._label = label
        self._root_concept_code = root_concept_code
        self._coding_scheme_name = coding_scheme_name
        self._coding_scheme_version = coding_scheme_version
        self._association_name = association_name
        self._direction = direction
        self._level = level
        self._delimiter = delimiter
        return

    @property
    def label(self):
        return self._label

    @label.setter
    def label(self,value):
        self._label = value

    @property
    def root_concept_code(self):
        return self._root_concept_code

    @root_concept_code.setter
    def root_concept_code(self,value):
        self._root_concept_code = value

    @property
    def coding_schema_name(self):
        return self._coding_schema_name

    @coding_schema_name.setter
    def coding_schema_name(self,value):
        self._coding_schema_name = value

    @property
    def coding_schema_version(self):
        return self._coding_schema_version

    @coding_schema_version.setter
    def coding_schema_version(self,value):
        self._coding_schema_version = value

    @property
    def association_name(self):
        return self._association_name

    @association_name.setter
    def association_name(self,value):
        self._association_name = value

    @property
    def direction(self):
        return self._direction

    @direction.setter
    def direction(self,value):
        self._direction = value

    @property
    def level(self):
        return self._level

    @level.setter
    def level(self,value):
        self._level = value

    @property
    def delimiter(self):
        return self._delimiter

    @delimiter.setter
    def delimiter(self,value):
        self._delimiter = value

    @property
    def columns(self):
        return self._columns

    @columns.setter
    def columns(self,value):
        self._columns = value

    def __str__(self):
        lines = []
        lines.append("label: " + self._label)
        lines.append("root_concept_code: " + self._root_concept_code)
        lines.append("coding_scheme_name: " + self._coding_scheme_name)
        lines.append("coding_scheme_version: " + self._coding_scheme_version)
        lines.append("association_name: " + self._association_name)
        lines.append("direction: " + self._direction)
        lines.append("level: " + self._level)
        lines.append("delimiter: " + self._delimiter)
        return "\n".join(lines)
