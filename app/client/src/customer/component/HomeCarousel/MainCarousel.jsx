import { mainCarouselData } from './MainCaroselData';
import AliceCarousel from 'react-alice-carousel';
import 'react-alice-carousel/lib/alice-carousel.css';



function MainCarousel() {

    const items = mainCarouselData.map((item) => <img className="carousel-pointer" src={item.image} alt="" />)

    return (
        // <swiper-container loop="true" navigation="true" autoplay-delay="1000" pagination="true"  slides-per-view="1" >
        //     {items}
        //     </swiper-container>
        <AliceCarousel
     disableButtonsControls
        items={items}
        autoPlay
        autoPlayInterval={1000}
        infinite
        controlsStrategy="alternate"
    />
    )
}

export default MainCarousel;