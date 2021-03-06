import React, { useState, useRef, useEffect, useCallback } from "react";
import {Button,Input, Form, FormGroup, Label} from 'reactstrap';
import axios from 'axios';
import { setHours, setMinutes } from 'date-fns'

import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";

import "../styles/TagFormComponentCss.css";

import { registerLocale} from  "react-datepicker";
import ru from 'date-fns/locale/ru';
registerLocale('ru', ru)


export default function TagFormComponent(props) {
    const [currentTag, setCurrentTag] = useState("Not picked");
    const [page_num, setPageNum] = useState(props.currentPage);
    const [totalPages, setTotalPages] = useState(props.totalPages);
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());

    const startDateRef = useRef();
    startDateRef.current = startDate;

    
    const endDateRef = useRef();
    endDateRef.current = endDate;

    
    const page_numRef = useRef();
    page_numRef.current =  page_num;

    
    const currentTagRef = useRef();
    currentTagRef.current = currentTag;

    const [tagsData,setTagsData]=useState([])

    const [isSending, setIsSending] = useState(false)
    const isMounted = useRef(true)


   

    useEffect(() => {
          isMounted.current = false
          axios.get("/api/v1/news/tags")
          .then(res=>{
              setTagsData(res.data)
            })
            .catch(err=>{
              console.log(err);
            })
            console.log(page_num)
            console.log(startDate)
            console.log(endDate)
            console.log(currentTag)
      }, [page_num,startDate,endDate,currentTag])

    function incrementPage() {
      if (page_num === totalPages) {
        console.log("no action")
        return;
      }
        console.log("i've incremented")
        setPageNum(page_num + 1)
    }
    
    function decrementPage() {
      console.log(page_num)
      if (page_num === 0) {
        console.log("no action")
        return;
      }
      console.log("i've decremented")
      setPageNum(page_num - 1)
  }
    
      const sendRequest = useCallback(async (event) => {
        event.preventDefault();
        if (isSending) return
        // update state
        setIsSending(true)
        // send the actual request
        let dict = {
            "createdFrom" : startDateRef.current,
            "createdTo" : endDateRef.current,
            "tag" : currentTagRef.current,
            "page" : page_numRef.current
        }
        props.onNewsGetHandler(dict);
        // once the request is sent, update state again
        if (isMounted.current) // only update if we are still mounted
            console.log("button disabled")
          setIsSending(false)
      }, [isSending]) 


    return (
        <Form name="newsInfo"onSubmit={sendRequest}>
            <FormGroup className="w-100"> 
            <div className="d-flex justify-content-between">
                <div style={{display: "inline-block", paddingTop: "5px", paddingLeft:"5px", marginRight:"5px"}}>{page_num}-{totalPages}</div>
                <button name="prev" disabled={isSending} className="btn btn-primary btn-sm" style={{marginBottom:"5px", marginRight:"5px"}} type="submit" value="???????????????????? ????????????????" onClick={decrementPage} color="primary">???????????????????? ????????????????</button>
                <button name="next" disabled={isSending} className="btn btn-primary btn-sm" style={{marginBottom:"5px", marginRight:"5px"}} type="submit" value="?????????????????? ????????????????" onClick={incrementPage} color="primary">?????????????????? ????????????????</button>
                <button name="add" disabled={isSending} className="btn btn-primary btn-sm" style={{marginBottom:"5px", marginRight:"5px"}} type="submit" value="??????????????????" color="primary"> + </button>
            </div>

            </FormGroup>
            <FormGroup>
                <Input
                    id="exampleSelect"
                    name="select"
                    type="select"
                    onChange={(e) => setCurrentTag(e.target.value)}
                    >
                    <option disabled selected>???????????????? ????????</option>
                    {tagsData.map((data,id) => (
                        <option key={id}>{data}</option>
                        ))}
                </Input>
            </FormGroup>
            <FormGroup style={{marginBottom: "5px", marginTop: "5px"}}>
            <DatePicker
            selected={startDate}
            onChange={(date) => setStartDate(date)}
            showTimeSelect
            dateFormat="MMMM d, yyyy HH:mm"
            locale="ru"
            />
            <DatePicker
            selected={endDate}
            onChange={(date) => setEndDate(date)}
            showTimeSelect
            dateFormat="MMMM d, yyyy HH:mm"
            locale="ru"
            />
            </FormGroup>          
        </Form>
    );
}
