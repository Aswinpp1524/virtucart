import { Grid } from '@mui/material';
import React from 'react'
import AdjustIcon from '@mui/icons-material/Adjust';
import { useNavigate } from 'react-router-dom';

const OrderCard = () => {
    const navigate = useNavigate();

    return (
        <div onClick={() => navigate(`/account/order/${5}`)} className='text-left shadow-lg hover:shadow-2xl border p-5'>
            <Grid container spacing={2} sx={{justifyContent:"space-between"}} >
                <Grid item xs={6} >
                    <div className='flex cursor-pointer'>
                    <img className='w-[5rem] h-[5rem] object-cover object-top' src='https://rukminim2.flixcart.com/image/832/832/xif0q/kurta/f/o/h/xxl-nairamaroon-marisy-original-imagrndkkyvrj9ff.jpeg?q=70' alt=''/>
                    <div className='ml-5 space-y-1'>
                        <p className='mb-2'>
                        Women Printed Viscose Rayon A-line Kurta 
                        </p>
                        <p className='opacity-50 text-xs font-semibold'>Size: M</p>
                        <p className='opacity-50 text-xs font-semibold'>Color: Black</p>
                    </div>
                    </div>
                   
                </Grid>
                <Grid item xs={2}>
                ₹425
                </Grid>
                <Grid item xs={4}>
                  { true &&
                  <div>
                  <p>
                    <AdjustIcon sx={{width:"15px", height:"15px"}} className='text-green-600 mr-2 text-sm'/>
                        <span>
                            Delivered On March 03
                        </span>
                    </p>
                    <p className='text-xs'>
                        Your Item Has Been Delivered
                    </p>
                    </div>
}
                   { false && <p>
                        <span>
                           Expected Delivery On Mar 03
                        </span>
                    </p>}
                </Grid>
            </Grid>    
        </div>
    )
}

export default OrderCard;
