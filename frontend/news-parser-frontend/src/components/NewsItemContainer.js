import React from "react";
import myImage from 'C:/Users/Niga/Desktop/glinomesa/depression.png'
import myImage1 from 'C:/Users/Niga/Downloads/test.jpg'

let imgs = [
    "https://cdnn21.img.ria.ru/images/07e5/05/18/1733632342_0:8:1280:728_436x0_80_0_0_2a4c7a06cfb20a80a0b14a38ae89c3ec.jpg.webp",
    'C:/Users/Niga/Downloads/test.jpg'
  ];

function NewsItemContainer() {

    return ( 
        <div className="Item">            
            <div className="title">
                <p>amogus</p>
            </div>
            <div>
                <div class="item_content">
                    <a href="https://ria.ru/20211116/proekty-1759354280.html" class="item_image">
                        <picture>
                            <source media={"(min-width: 480px)"} media-type="ar16x9" srcset="https://cdnn21.img.ria.ru/images/07e5/05/18/1733632342_0:8:1280:728_436x0_80_0_0_2a4c7a06cfb20a80a0b14a38ae89c3ec.jpg.webp"></source>
                            <source media={"(min-width: 375px)"} media-type="ar4x3" srcset="https://cdnn21.img.ria.ru/images/07e5/05/18/1733632342_150:0:1131:736_186x0_80_0_0_15ee1f75a12993f1a3f43a51e5803723.jpg.webp"></source>
                            <source media="(min-width: 0px)" media-type="ar1x1" srcset="https://cdnn21.img.ria.ru/images/07e5/05/18/1733632342_272:0:1008:736_140x0_80_0_0_5de169b53a9149c5a6e3199b96461dec.jpg.webp"></source>
                            <img src="" alt="MDN"></img>
                        </picture>
                    </a>
                    <a href="https://ria.ru/20211116/proekty-1759354280.html" class="list-item__title color-font-hover-only">ВЭБ.РФ поддержит "проекты-маяки" в сфере технологического развития </a>
                </div>
                <div className="item_info">

                </div>
            </div>

        </div>
    );
}

export default NewsItemContainer;