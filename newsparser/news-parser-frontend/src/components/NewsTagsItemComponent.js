import React, { useState, useEffect } from 'react';


function NewsTagsItemComponent(props) {

    return (  
        <div className="col-xs-1 col-sm-2 col-md-4 col-lg-2 col-xl-1">
        {props.name}
        </div>
    );
}

export default NewsTagsItemComponent;