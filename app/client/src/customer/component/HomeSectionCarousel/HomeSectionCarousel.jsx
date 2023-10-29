import React, { useState } from 'react'
import HomeSectionCard from '../HomeSectionCard/HomeSectionCard';
import AliceCarousel from 'react-alice-carousel';
import 'react-alice-carousel/lib/alice-carousel.css';
import { Button } from '@mui/material';
import KeyboardArrowLeftIcon from '@mui/icons-material/KeyboardArrowLeft';


const HomeSectionCarousel = ({data, sectionName}) => {

    const [activeIndex, setActiveIndex]=useState(0);
    const responsive = {
        0: { items: 1 },
        720: { items: 3 },
        1024: { items: 5.2 },
    };

    const slidePrev=()=>setActiveIndex(activeIndex - 1);
    const slideNext=()=>setActiveIndex(activeIndex + 1);

    const syncActiveIndex = ({item})=>setActiveIndex(item);
    const items = data.slice(0,10).map((item) => <HomeSectionCard product={item}/>)
    return (
        <div className='border'>
            <h2 className='text-2xl font-extrabold text-gary-800 py-5 text-left'>{sectionName}</h2>
            <div className='relative p-5'>
                <AliceCarousel
                    disableButtonsControls
                    items={items}
                    responsive={responsive}
                    disableDotsControls
                    onSlideChanged={syncActiveIndex}
                    activeIndex={activeIndex}
                />
               { activeIndex !== items.length - 5 && <Button variant='contained' className='z-50'
                onClick={slideNext}
                sx={{
                    position: 'absolute', top: "8rem",
                    right: "0rem", transform: "translateX(50%) rotate(90deg)", bgcolor: "white"
                }} aria-label='next'>
                    <KeyboardArrowLeftIcon sx={{ transform: 'rotate(90deg)', color: 'black' }} />
                </Button>
}
              {activeIndex !== 0 &&  <Button variant='contained' className='z-50' 
                onClick={slidePrev}
                sx={{
                    position: 'absolute', top: "8rem",
                    left: "0rem", transform: "translateX(-50%) rotate(-90deg)", bgcolor: "white"
                }} aria-label='next'>
                    <KeyboardArrowLeftIcon sx={{ transform: 'rotate(90deg)', color: 'black' }} />
                </Button>
                }
            </div>
        </div>
    )
}

export default HomeSectionCarousel;
