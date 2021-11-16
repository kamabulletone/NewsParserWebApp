import React, { useState } from "react";
import {Button,Input, Form, FormGroup, Label} from 'reactstrap';

import { setHours, setMinutes } from 'date-fns'

import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";

import "../styles/TagFormComponentCss.css";

import { registerLocale} from  "react-datepicker";
import ru from 'date-fns/locale/ru';
registerLocale('ru', ru)


export default function TagFormComponent() {
    const [value, setValue] = useState('Экономика')
    const [startDate, setStartDate] = useState(new Date());
    
    const [endDate, setEndDate] = useState(new Date());

    function handleChange(event) {
        setValue(value);
      }
    
    function handleSubmit(event) {
        alert('Отправленное имя: ' + value);
        alert('Отправленное имя: ' + startDate);
        alert('Отправленное имя: ' + endDate);
        event.preventDefault();
      }

    return (
        <Form onSubmit={handleSubmit}>
            <FormGroup className="w-100"> 
                <Button className="float-right" style={{float: "right", marginBottom:"5px"}}type="submit" value="Отправить" color="primary"> + </Button>
                <Button className="float-right" style={{float: "right", marginBottom:"5px", marginRight: "5px"}}type="submit" value="Следующая страница" color="primary">Следующая страница</Button>
            </FormGroup>
            <FormGroup>
                <Input
                    id="exampleSelect"
                    name="select"
                    type="select"
                    >
                    <option>
                        1
                    </option>
                    <option>
                        2
                    </option>
                    <option>
                        3
                    </option>
                    <option>
                        4
                    </option>
                    <option>
                        5
                    </option>
                </Input>
            </FormGroup>
            <FormGroup style={{marginBottom: "5px", marginTop: "5px"}}>
            <DatePicker
            selected={startDate}
            onChange={(date) => setStartDate(date)}
            showTimeSelect
            minTime={setHours(setMinutes(new Date(), 0), 0)}
            maxTime={setHours(setMinutes(new Date(), 23), 59)}
            dateFormat="MMMM d, yyyy h:mm aa"
            locale="ru"
            />
            <DatePicker
            selected={endDate}
            onChange={(date) => setEndDate(date)}
            showTimeSelect
            minTime={setHours(setMinutes(new Date(), 0), 0)}
            maxTime={setHours(setMinutes(new Date(), 23), 59)}
            dateFormat="MMMM d, yyyy h:mm aa"
            locale="ru"
            />
            </FormGroup>          
        </Form>
    );
}
