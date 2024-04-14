import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Destination from './destination';
import DestinationDetail from './destination-detail';
import DestinationUpdate from './destination-update';
import DestinationDeleteDialog from './destination-delete-dialog';

const DestinationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Destination />} />
    <Route path="new" element={<DestinationUpdate />} />
    <Route path=":id">
      <Route index element={<DestinationDetail />} />
      <Route path="edit" element={<DestinationUpdate />} />
      <Route path="delete" element={<DestinationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DestinationRoutes;
