import express from 'express';
import controller from '../controller';

let router = express();
/*this routes the message to controller
*/
router.use('/route',controller);

export default router;
