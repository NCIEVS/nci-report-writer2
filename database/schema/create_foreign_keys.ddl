system echo 'Adding Foreign Keys to report_task'
ALTER TABLE report_task ADD CONSTRAINT fk_report_task_1
    FOREIGN KEY (status)
    REFERENCES lk_report_status(name);

ALTER TABLE report_task ADD CONSTRAINT fk_report_task_2
    FOREIGN KEY (report_template_id)
    REFERENCES report_template(id);

system echo 'Adding Foreign Keys to report_template_concept_list'
ALTER TABLE report_template_concept_list ADD CONSTRAINT fk_report_template_concept_list_1
    FOREIGN KEY (report_template_id)
    REFERENCES report_template(id);

system echo 'Adding Foreign Keys to report_template'
ALTER TABLE report_template ADD CONSTRAINT fk_report_template_1
    FOREIGN KEY (status)
    REFERENCES lk_report_template_status(name);

ALTER TABLE report_template ADD CONSTRAINT fk_report_template_2
    FOREIGN KEY (type)
    REFERENCES lk_report_template_type(name);

ALTER TABLE report_template ADD CONSTRAINT fk_report_template_3
    FOREIGN KEY (association)
    REFERENCES lk_association(name);



system echo 'Adding Foreign Keys to report_template_column'
ALTER TABLE report_template_column ADD CONSTRAINT fk_report_template_column_1
    FOREIGN KEY (report_template_id)
    REFERENCES report_template(id);

ALTER TABLE report_template_column ADD CONSTRAINT fk_report_template_column_2
    FOREIGN KEY (display)
    REFERENCES lk_display(name);

ALTER TABLE report_template_column ADD CONSTRAINT fk_report_template_column_3
    FOREIGN KEY (property_type)
    REFERENCES lk_property_type(name);

ALTER TABLE report_template_column ADD CONSTRAINT fk_report_template_column_4
    FOREIGN KEY (property)
    REFERENCES lk_property(code);

ALTER TABLE report_template_column ADD CONSTRAINT fk_report_template_column_5
    FOREIGN KEY (source)
    REFERENCES lk_source(name);

ALTER TABLE report_template_column ADD CONSTRAINT fk_report_template_column_6
    FOREIGN KEY (source_group)
    REFERENCES lk_group(name);

ALTER TABLE report_template_column ADD CONSTRAINT fk_report_template_column_7
    FOREIGN KEY (subsource)
    REFERENCES lk_subsource(name);

ALTER TABLE report_template_column ADD CONSTRAINT fk_report_template_column_8
    FOREIGN KEY (attr)
    REFERENCES lk_attr(name);
