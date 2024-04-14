import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './tour.reducer';

export const Tour = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const tourList = useAppSelector(state => state.tour.entities);
  const loading = useAppSelector(state => state.tour.loading);
  const totalItems = useAppSelector(state => state.tour.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="tour-heading" data-cy="TourHeading">
        <Translate contentKey="xploraAdminApp.tour.home.title">Tours</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="xploraAdminApp.tour.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/tour/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="xploraAdminApp.tour.home.createLabel">Create new Tour</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tourList && tourList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="xploraAdminApp.tour.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  <Translate contentKey="xploraAdminApp.tour.code">Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('mode')}>
                  <Translate contentKey="xploraAdminApp.tour.mode">Mode</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('mode')} />
                </th>
                <th className="hand" onClick={sort('duration')}>
                  <Translate contentKey="xploraAdminApp.tour.duration">Duration</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('duration')} />
                </th>
                <th className="hand" onClick={sort('petFriendly')}>
                  <Translate contentKey="xploraAdminApp.tour.petFriendly">Pet Friendly</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('petFriendly')} />
                </th>
                <th className="hand" onClick={sort('kidsAllowed')}>
                  <Translate contentKey="xploraAdminApp.tour.kidsAllowed">Kids Allowed</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('kidsAllowed')} />
                </th>
                <th className="hand" onClick={sort('availableFromDate')}>
                  <Translate contentKey="xploraAdminApp.tour.availableFromDate">Available From Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('availableFromDate')} />
                </th>
                <th className="hand" onClick={sort('availableToDate')}>
                  <Translate contentKey="xploraAdminApp.tour.availableToDate">Available To Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('availableToDate')} />
                </th>
                <th className="hand" onClick={sort('enabled')}>
                  <Translate contentKey="xploraAdminApp.tour.enabled">Enabled</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('enabled')} />
                </th>
                <th className="hand" onClick={sort('initialPrice')}>
                  <Translate contentKey="xploraAdminApp.tour.initialPrice">Initial Price</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('initialPrice')} />
                </th>
                <th className="hand" onClick={sort('price')}>
                  <Translate contentKey="xploraAdminApp.tour.price">Price</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('price')} />
                </th>
                <th className="hand" onClick={sort('badge')}>
                  <Translate contentKey="xploraAdminApp.tour.badge">Badge</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('badge')} />
                </th>
                <th className="hand" onClick={sort('rating')}>
                  <Translate contentKey="xploraAdminApp.tour.rating">Rating</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('rating')} />
                </th>
                <th className="hand" onClick={sort('widgetId')}>
                  <Translate contentKey="xploraAdminApp.tour.widgetId">Widget Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('widgetId')} />
                </th>
                <th className="hand" onClick={sort('externalId')}>
                  <Translate contentKey="xploraAdminApp.tour.externalId">External Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('externalId')} />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="xploraAdminApp.tour.createdDate">Created Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdDate')} />
                </th>
                <th className="hand" onClick={sort('defaultImage')}>
                  <Translate contentKey="xploraAdminApp.tour.defaultImage">Default Image</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('defaultImage')} />
                </th>
                <th className="hand" onClick={sort('defaultImageData')}>
                  <Translate contentKey="xploraAdminApp.tour.defaultImageData">Default Image Data</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('defaultImageData')} />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.tour.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.tour.meetingPoint">Meeting Point</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.tour.finishPoint">Finish Point</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.tour.destination">Destination</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tourList.map((tour, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/tour/${tour.id}`} color="link" size="sm">
                      {tour.id}
                    </Button>
                  </td>
                  <td>{tour.code}</td>
                  <td>
                    <Translate contentKey={`xploraAdminApp.TourMode.${tour.mode}`} />
                  </td>
                  <td>{tour.duration}</td>
                  <td>{tour.petFriendly ? 'true' : 'false'}</td>
                  <td>{tour.kidsAllowed ? 'true' : 'false'}</td>
                  <td>
                    {tour.availableFromDate ? (
                      <TextFormat type="date" value={tour.availableFromDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {tour.availableToDate ? <TextFormat type="date" value={tour.availableToDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{tour.enabled ? 'true' : 'false'}</td>
                  <td>{tour.initialPrice}</td>
                  <td>{tour.price}</td>
                  <td>{tour.badge}</td>
                  <td>{tour.rating}</td>
                  <td>{tour.widgetId}</td>
                  <td>{tour.externalId}</td>
                  <td>{tour.createdDate ? <TextFormat type="date" value={tour.createdDate} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{tour.defaultImage}</td>
                  <td>
                    {tour.defaultImageData ? (
                      <div>
                        {tour.defaultImageDataContentType ? (
                          <a onClick={openFile(tour.defaultImageDataContentType, tour.defaultImageData)}>
                            <img
                              src={`data:${tour.defaultImageDataContentType};base64,${tour.defaultImageData}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {tour.defaultImageDataContentType}, {byteSize(tour.defaultImageData)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{tour.createdBy ? tour.createdBy.login : ''}</td>
                  <td>{tour.meetingPoint ? <Link to={`/place/${tour.meetingPoint.id}`}>{tour.meetingPoint.code}</Link> : ''}</td>
                  <td>{tour.finishPoint ? <Link to={`/place/${tour.finishPoint.id}`}>{tour.finishPoint.code}</Link> : ''}</td>
                  <td>{tour.destination ? <Link to={`/destination/${tour.destination.id}`}>{tour.destination.code}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/tour/${tour.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/tour/${tour.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/tour/${tour.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="xploraAdminApp.tour.home.notFound">No Tours found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={tourList && tourList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Tour;
